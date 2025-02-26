/** Project: Lab3
 * Purpose Details: To create a database
 * Course:IST140
 * Author:Ulrich Commodore
 * Date Developed:02/21/2025
 * Last Date Changed:02/24/2025
 * Rev:

 */

public class Customer {
    private int id;
    private String  firstname;
    private String lastname;
    private String age;
    private String email_address;

    public Customer(int id,String firstname,String lastname,String age,String email_address) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.age = age;
        this.email_address = email_address;
    }
    public int getId() {
        return id;
    }
    public int setId(int id) {
        this.id = id;
        return id;
    }
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
    public String getAge() {
        return age;
    }
    public void setAge(String age) {
        this.age = age;
    }
    public String getEmail_address() {
        return email_address;
    }
    public void setEmail_address(String email_address) {
        this.email_address = email_address;
    }
    @Override
    public String toString(){
        return "Customer{"+
                "ID="+ id +
                ", First name=' "+ firstname +'\'' +
                ", Last name=' " + lastname +'\'' +
                ", Age=' " + age + '\'' +
                ", Email address=' " + email_address + '\'' +
                '}';
    }
}
