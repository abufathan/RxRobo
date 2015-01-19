package id.netzme.android.rxrobo;

import android.util.Log;

import org.robobinding.annotation.DependsOnStateOf;
import org.robobinding.annotation.PresentationModel;
import org.robobinding.presentationmodel.HasPresentationModelChangeSupport;
import org.robobinding.presentationmodel.PresentationModelChangeSupport;

import rx.subjects.ReplaySubject;

/**
 * Created by noval on 1/14/15.
 */
@PresentationModel
public class MainModel implements HasPresentationModelChangeSupport {
    private String password = "";
    private String nickname = "";
    private ReplaySubject<String> nicknameSubject;
    private ReplaySubject<String> passwordSubject;
    private Boolean emptyFields = true;
    private PresentationModelChangeSupport pmSupport;

    public MainModel(ReplaySubject<String> nicknameSubject, ReplaySubject<String> passwordSubject) {
        this.nicknameSubject = nicknameSubject;
        this.passwordSubject = passwordSubject;
        pmSupport = new PresentationModelChangeSupport(this);
    }

    public void submitLogin() {
        Log.d("MAIN MODEL", "Submit Login");
    }


    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
        nicknameSubject.onNext(nickname);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        passwordSubject.onNext(password);
    }

    @DependsOnStateOf("emptyFields")
    public boolean isButtonLoginEnabled() {
        return emptyFields;
    }

    public boolean isEmptyFields() {
        return emptyFields;
    }

    public void setEmptyFields(boolean emptyFields) {
        this.emptyFields = emptyFields;
        pmSupport.firePropertyChange("emptyFields");
    }

    @Override
    public PresentationModelChangeSupport getPresentationModelChangeSupport() {
        return pmSupport;
    }
}
