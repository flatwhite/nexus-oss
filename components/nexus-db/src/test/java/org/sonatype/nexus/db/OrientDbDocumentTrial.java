package org.sonatype.nexus.db;

import org.sonatype.sisu.litmus.testsupport.TestSupport;

import com.orientechnologies.orient.core.db.document.ODatabaseDocumentPool;
import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;
import com.orientechnologies.orient.core.id.ORID;
import com.orientechnologies.orient.core.id.ORecordId;
import com.orientechnologies.orient.core.record.ORecordInternal;
import com.orientechnologies.orient.core.record.impl.ODocument;
import com.orientechnologies.orient.core.storage.ORecordMetadata;
import org.apache.shiro.codec.Hex;
import org.junit.After;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

/**
 * Trials of using OrientDB using document-api.
 */
public class OrientDbDocumentTrial
    extends TestSupport
{
  private ODatabaseDocumentTx createDatabase() {
    return new ODatabaseDocumentTx("memory:testdb").create();
  }

  private ODatabaseDocumentTx openDatabase() {
    return new ODatabaseDocumentTx("memory:testdb").open("admin", "admin");
  }

  private ODocument createPerson(final ODatabaseDocumentTx db) {
    ODocument doc = db.newInstance("Person");
    doc.field("name", "Luke");
    doc.field("surname", "Skywalker");
    doc.field("city", new ODocument("City")
        .field("name", "Rome")
        .field("country", "Italy"));
    doc.save();
    return doc;
  }

  @After
  public void tearDown() throws Exception {
    try (ODatabaseDocumentTx db = openDatabase()) {
      db.drop();
    }
  }

  @Test
  public void documentTx() throws Exception {
    try (ODatabaseDocumentTx db = createDatabase()) {
      ODocument doc = createPerson(db);
    }
  }

  @Test
  public void globalPool() throws Exception {
    // first ensure the database is created, and close the connection
    createDatabase().close();

    // now we should be able to get a pooled connection
    try (ODatabaseDocumentTx db = ODatabaseDocumentPool.global().acquire("memory:testdb", "admin", "admin")) {
      ODocument doc = createPerson(db);
    }
  }

  @Test
  public void recordIdEncoding() throws Exception {
    try (ODatabaseDocumentTx db = createDatabase()) {
      ODocument doc = createPerson(db);
      log("New Document: {}", doc);

      ORID rid = doc.getIdentity();
      log("RID: {}", rid);

      String encoded = Hex.encodeToString(rid.toStream());
      log("Hex Encoded: {}", encoded);

      ORID decoded = new ORecordId().fromStream(Hex.decode(encoded));
      log("Decoded RID: {}", decoded);

      assertThat(decoded, is(rid));

      doc = db.getRecord(decoded);
      log("Fetched Document: {}", doc);
    }
  }

  @Test
  public void documentExistance() throws Exception {
    try (ODatabaseDocumentTx db = createDatabase()) {
      ODocument doc = createPerson(db);
      log("Document: {}", doc);

      ORID rid = doc.getIdentity();
      log("RID: {}", rid);

      ORecordMetadata md = db.getRecordMetadata(rid);
      log("Metadata: {}", md);
      assertThat(md, notNullValue());
    }
  }

  @Test
  public void loadNonExistingDocument() throws Exception {
    try (ODatabaseDocumentTx db = createDatabase()) {
      ORID rid = new ORecordId("#1:2"); // NOTE: #1:1 will return a record, #1:2 will return null
      log("RID: {}", rid);

      ORecordMetadata md = db.getRecordMetadata(rid);
      log("Metadata: {}", md);
      assertThat(md, nullValue());

      ORecordInternal record = db.load(rid);
      log("Record: {}", record);
      assertThat(record, nullValue());
    }
  }
}
