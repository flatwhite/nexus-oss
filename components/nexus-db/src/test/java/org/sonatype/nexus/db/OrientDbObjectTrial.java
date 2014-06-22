package org.sonatype.nexus.db;

import javax.persistence.Id;
import javax.persistence.Version;

import org.sonatype.sisu.litmus.testsupport.TestSupport;

import com.orientechnologies.orient.object.db.OObjectDatabaseTx;
import org.junit.Test;

/**
 * Trials of using OrientDB object-api.
 */
public class OrientDbObjectTrial
    extends TestSupport
{
  public static class Person
  {
    @Id
    private Object id;

    @Version
    private Object version;

    private String firstName;

    private String lastName;

    public Object getId() {
      return id;
    }

    public Object getVersion() {
      return version;
    }

    public String getFirstName() {
      return firstName;
    }

    public void setFirstName(final String firstName) {
      this.firstName = firstName;
    }

    public String getLastName() {
      return lastName;
    }

    public void setLastName(final String lastName) {
      this.lastName = lastName;
    }

    @Override
    public String toString() {
      return getClass().getSimpleName() + "{" +
          "id=" + id +
          ", version=" + version +
          ", firstName='" + firstName + '\'' +
          ", lastName='" + lastName + '\'' +
          '}';
    }
  }

  @Test
  public void objectTx() throws Exception {
    try (OObjectDatabaseTx db = new OObjectDatabaseTx("memory:test").create()) {
      db.getEntityManager().registerEntityClass(Person.class);
      Person newPerson = db.newInstance(Person.class);
      newPerson.setFirstName("James");
      newPerson.setLastName("Bond");
      db.save(newPerson);

      for (Person person : db.browseClass(Person.class)) {
        log("{}v{} -> {} {}", person.getId(), person.getVersion(), person.getFirstName(), person.getLastName());

        // NOTE: The javaassist proxy here doesn't properly toString(), need to have detached object for that to work??
        log("toString: {}", person);
        log("detached toString: {}", db.detach(person, true));
      }

      db.drop();
    }
  }
}
