/**
 * Classe permettant de réprésenter un utilisateur
 */

package model;

public class User {
    private Integer idUser;
    private String firstname;
    private String lastname;
    private String username;
    private String password;
    private String email;

    public User(Integer idUser, String firstname, String lastname, String username, String password, String email){
        this.idUser = idUser;
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public User(String firstname, String lastname, String username, String password, String email){
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public Integer getIdUser() { return idUser; }

    public void setIdUser(Integer idUser) { this.idUser = idUser; }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getUsername(){
        return username;
    }

    public void setUsername(String username) { this.username = username; }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setMail(String email) {
        this.email = email;
    }
}
