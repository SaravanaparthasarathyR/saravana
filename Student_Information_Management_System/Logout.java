import javax.swing.*;

public class Logout {

    public Logout(JFrame currentFrame) {
        currentFrame.dispose();
        new LoginModule();
    }
}