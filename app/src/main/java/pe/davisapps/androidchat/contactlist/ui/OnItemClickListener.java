package pe.davisapps.androidchat.contactlist.ui;


import pe.davisapps.androidchat.contactlist.entities.User;


public interface OnItemClickListener {
    void onItemClick(User user);
    void onItemLongClick(User user);
}
