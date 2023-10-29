package api;

public class UnSuccessReg {
    public String error;


    public UnSuccessReg(){

    }
    public UnSuccessReg(String error) {
        this.error = error;
    }

    public String error(){
        return error;
    }
}
