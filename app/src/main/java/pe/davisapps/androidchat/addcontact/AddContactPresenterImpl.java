package pe.davisapps.androidchat.addcontact;

import org.greenrobot.eventbus.Subscribe;

import pe.davisapps.androidchat.addcontact.events.AddContactEvent;
import pe.davisapps.androidchat.addcontact.ui.AddContactView;
import pe.davisapps.androidchat.lib.EventBus;
import pe.davisapps.androidchat.lib.GreenRobotEventBus;


public class AddContactPresenterImpl implements AddContactPresenter {
    EventBus eventBus;
    AddContactView addContactView;
    AddContactInteractor addContactInteractor;

    public AddContactPresenterImpl(AddContactView addContactView) {
        this.eventBus = GreenRobotEventBus.getInstance();
        this.addContactView = addContactView;
        this.addContactInteractor = new AddContactInteractorImpl(new AddContactRepositoryImpl());
    }

    @Override
    public void onShow() {
        eventBus.register(this);
    }

    @Override
    public void onDestroy() {
        addContactView = null;
        eventBus.unregister(this);
    }

    @Override
    public void addContact(String email) {
        addContactView.hideInput();
        addContactView.showProgress();
        this.addContactInteractor.addContact(email);
    }

    @Override
    @Subscribe
    public void onEventMainThread(AddContactEvent event) {
        if (addContactView != null) {
            addContactView.hideProgress();
            addContactView.showInput();

            if (event.isError()) {
                addContactView.contactNotAdded();
            } else {
                addContactView.contactAdded();
            }
        }
    }
}
