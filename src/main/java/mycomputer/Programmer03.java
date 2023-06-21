package mycomputer;



public class Programmer03 {
    private String name;
    private  String address;
    private mycomputer.Computer03 computer;

    public Programmer03(String name, String address, mycomputer.Computer03 computer) {
        this.name = name;
        this.address = address;
        this.computer = computer;
    }

    @Override
    public String toString() {
        String imsi = "";
        imsi += "Programmer Information\n";
        imsi += "name : " + this.name + "\n";
        imsi += "address : " + this.address + "\n\n";
        imsi += "Computer Information\n";
        imsi += "" + this.computer.toString() + "\n";

        return imsi;
    }
}
