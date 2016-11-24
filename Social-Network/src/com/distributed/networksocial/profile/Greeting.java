package com.distributed.networksocial.profile;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Parent;

import java.lang.String;
import java.util.Date;

/**
 * The @Entity tells Objectify about our entity.  We also register it in {@link OfyHelper}
 * Our primary key @Id is set automatically by the Google Datastore for us.
 *
 * We add a @Parent to tell the object about its ancestor. We are doing this to support many
 * guestbooks.  Objectify, unlike the AppEngine library requires that you specify the fields you
 * want to index using @Index.  Only indexing the fields you need can lead to substantial gains in
 * performance -- though if not indexing your data from the start will require indexing it later.
 *
 * NOTE - all the properties are PUBLIC so that we can keep the code simple.
 **/
@Entity
public class Greeting {
  @Parent Key<Profile> theProfile;
  @Id public Long id;

  public String author_email;
  public String author_id;
  public String content;
  @Index public Date date;

  /**
   * Simple constructor just sets the date
   **/
  public Greeting() {
    date = new Date();
  }

  /**
   * A convenience constructor
   **/
  public Greeting(String profile, String content) {
    this();
    if( profile != null ) {
      theProfile = Key.create(Profile.class, profile);  // Creating the Ancestor key
    } else {
      theProfile = Key.create(Profile.class, "default");
    }
    this.content = content;
  }

  /**
   * Takes all important fields
   **/
  public Greeting(String profile, String content, String id, String email) {
    this(profile, content);
    author_email = email;
    author_id = id;
  }

}
//[END all]
