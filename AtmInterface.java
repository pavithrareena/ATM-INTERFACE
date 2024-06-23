import java.util.Scanner;

public class AtmInterface {
    static {
        System.out.println("+++++++++++++++++++++++++++++++++++++++++");
        System.out.println("!------------------ATM------------------!");
        System.out.println("+++++++++++++++++++++++++++++++++++++++++");
    }

    public static void main(String[] args) {
        Bank b = new Bank();
        b.setup();
    }
}

class AtmInvalidPinOrAcc extends Exception {
    @Override
    public String getMessage() {
        return "Invalid Card details! Try Again.";
    }
}

class InvalidOperation extends Exception {
    @Override
    public String getMessage() {
        return "Invalid operation performed! Try Again.";
    }
}

class AtmCardBlock extends Exception {
    @Override
    public String getMessage() {
        return "Your Card Blocked!. Please try contack Bank";
    }
}

class ATM {
    private int inAccNum;
    private int inPsw;
    Scanner sc = new Scanner(System.in);

    public void acceptIN() {
        System.out.println("-------------------------");
        System.out.println("Enter the Account Number");
        inAccNum = sc.nextInt();
        System.out.println("-------------------------");
        System.out.println("Enter your pin");
        inPsw = sc.nextInt();
    }

    public void validateIn() throws AtmInvalidPinOrAcc, InvalidOperation {
        boolean valid = true;
        Bank bank = new Bank();
        if (inAccNum == bank.getAccNum() && inPsw == bank.getPsw()) {
            while (valid) {
                System.out.println("____________________________________");
                System.out.println(
                        "1.check Balance\n2.Withdraw\n3.Deposite\n4.Logout\n5.Operation to be performed(1|2|3|4):-");
                int choice = sc.nextInt();
                System.out.println("____________________________________");
                switch (choice) {
                    case 1:
                        System.out.println("Available balance in your account XXXX XXXX " + bank.getAccNum()
                                + " is " + bank.getBalance());
                        break;
                    case 2:
                        System.out.println("_____________________________");
                        System.out.println("Enter the amount to withdraw:");
                        double amount = sc.nextDouble();
                        if (amount >= bank.getBalance()) {
                            System.out.println("insufficient balance!");
                            break;
                        } else {
                            bank.setBalance(bank.getBalance() - amount);
                            System.out.println("Collect your money: " + amount);
                            System.out.println("Available balance in your account XXXX XXXX " + bank.getAccNum()
                                    + " is " + bank.getBalance());
                            break;
                        }
                    case 3:
                        System.out.println("_________________________________");
                        System.out.println("Enter the amount to be deposited:");
                        double Amount = sc.nextDouble();
                        bank.setBalance(bank.getBalance() + Amount);
                        System.out.println("Deposited " + Amount + " to your account XXXX XXXX " + bank.getAccNum());
                        System.out.println("Available balance: " + bank.getBalance());
                        break;
                    case 4:
                        valid = false;
                        break;
                    default:
                        InvalidOperation inv = new InvalidOperation();
                        System.out.println(inv.getMessage());
                }
            }
        } else {
            AtmInvalidPinOrAcc ex = new AtmInvalidPinOrAcc();
            System.out.println("---------------------------------------------------");
            System.out.println(ex.getMessage());
            throw ex;
        }
    }
}

class Bank {
    private int AccNum = 1234;
    private int psw = 1234;
    private double balance = 0;

    public int getAccNum() {
        return AccNum;
    }

    public void setAccNum(int accNum) {
        AccNum = accNum;
    }

    public int getPsw() {
        return psw;
    }

    public void setPsw(int psw) {
        this.psw = psw;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void setup() {
        ATM a = new ATM();
        try {
            System.out.println("| please provide credentials to perform actions |");
            a.acceptIN();
            a.validateIn();
        } catch (Exception e) {
            try {
                System.out.println("please provide valid credentials to perform actions");
                a.acceptIN();
                a.validateIn();
            } catch (Exception f) {
                try {
                    System.out.println("please provide valid credentials to perform actions");
                    a.acceptIN();
                    a.validateIn();
                } catch (Exception g) {
                    AtmCardBlock ex = new AtmCardBlock();
                    System.err.println(ex.getMessage());
                }
            }
        }
    }
}
