package back_end_economizaja.model.cliente;

public enum ClienteRole {
    CLIENTE("cliente");


    private String role;
    ClienteRole(String role) {
     this.role = role;
    }

    public String getRole(){
        return role;
    }
}
