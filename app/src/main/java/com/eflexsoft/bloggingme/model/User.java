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
    private String bio;
    private String location;
    private String date;

    public User() {
    }

    public User(String firstName, String lastName, String email, String proPicUrl, String bio, String location,String date) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.proPicUrl = proPicUrl;
        this.bio = bio;
        this.location = location;
        this.date = date;
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

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
