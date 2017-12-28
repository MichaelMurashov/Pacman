package sample.model;

public interface IDatabase {

    void Connect();
    String CheckPassword(String userName, String password);
    void InsertNewUser(String userName, String password);

}
