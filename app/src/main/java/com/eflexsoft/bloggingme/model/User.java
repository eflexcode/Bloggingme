package com.eflexsoft.bloggingme.model;

public class User {

   /*   map.put("firstName",firstName);
                    map.put("lastName",lastName);
                    map.put("email",email);
                    map.put("proPicUrl","none");
*/

   private String firstName;
   private String lastName;
   private String email;
   private String proPicUrl;

    public User() {
    }

    public User(String firstName, String lastName, String email, String proPicUrl) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.proPicUrl = proPicUrl;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProPicUrl() {
        return proPicUrl;
    }

    public void setProPicUrl(String proPicUrl) {
        this.proPicUrl = proPicUrl;
    }
}
