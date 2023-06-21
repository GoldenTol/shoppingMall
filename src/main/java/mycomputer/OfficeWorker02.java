package mycomputer;

public class OfficeWorker02 {
    private String name;
    private String address;

    // 의존(dependency):하나의 클래스가 다른 클래스를 참조하고 있는 관계
    // 의존은 변경에 의하여 영향을 받는 관계
    private Computer02 computer; // 의존한다.(dependency)

    // Injection = 생성자 인젝션, setter 인젝션

    // constructor Injection : 생성자를 통한 데이터 입력
    public OfficeWorker02(String name, String address) {
        this.name = name;
        this.address = address;
        this.computer = new Computer02(); // 객체 생성

        this.computer.setCpu("인텔 cpu"); //setter Injection =  setter를 이용한 주입(Injection)이라고 부릅니다., 세터를 통한 대이터 입력(write)
        this.computer.setHdd("삼성 hdd");
        this.computer.setMainboard("엘지 mainboard");
    }

    @Override
    public String toString() {
        String imsi = "";
        imsi += "사원 Information\n";
        imsi += "name : " + this.name + "\n";
        imsi += "address : " + this.address + "\n\n";
        imsi += "Computer Information\n";
        imsi += "" + this.computer.toString() + "\n";

        return imsi;

    }


}
