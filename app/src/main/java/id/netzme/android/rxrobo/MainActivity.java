package id.netzme.android.rxrobo;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import org.robobinding.ViewBinder;
import org.robobinding.binder.BinderFactory;
import org.robobinding.binder.BinderFactoryBuilder;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func2;
import rx.subjects.ReplaySubject;


public class MainActivity extends ActionBarActivity {
    private MainModel mainModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ReplaySubject<String> nicknameSubject = ReplaySubject.create();
        ReplaySubject<String> passwordSubject = ReplaySubject.create();

        mainModel = new MainModel(nicknameSubject, passwordSubject);

        BinderFactory binderFactory = new BinderFactoryBuilder().build();
        ViewBinder viewBinder = binderFactory.createViewBinder(this);
        View rootView = viewBinder.inflateAndBind(R.layout.activity_main, mainModel);
        setContentView(rootView);

        observeTextField(nicknameSubject, passwordSubject);
    }

    private void observeTextField(ReplaySubject<String> nicknameSubject, ReplaySubject<String> passwordSubject) {
        Observable.combineLatest(nicknameSubject, passwordSubject, new Func2<String, String, Boolean>() {
            @Override
            public Boolean call(String nickname, String password) {
                return (nickname.isEmpty() || password.isEmpty());
            }
        }).subscribe(new Action1<Boolean>() {
            @Override
            public void call(Boolean aBoolean) {
                mainModel.setEmptyFields(!aBoolean);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
