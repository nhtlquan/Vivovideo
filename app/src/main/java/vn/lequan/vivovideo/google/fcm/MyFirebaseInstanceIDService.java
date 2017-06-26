package vn.lequan.vivovideo.google.fcm;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.smile.studio.libsmilestudio.utils.Debug;
import com.smile.studio.libsmilestudio.utils.SharedPreferencesUtils;

/**
 * Created by admin on 23/07/2016.
 */
public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    public static IAction action;

    @Override
    public void onTokenRefresh() {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Debug.e("GEN TOKEN: " + refreshedToken);
        sendRegistrationToServer(refreshedToken);

    }

    private void sendRegistrationToServer(String token) {
        Debug.e("SEND SERVER: " + token);
        SharedPreferencesUtils.putString(getApplication(), MyFirebaseInstanceIDService.class.getSimpleName(), token);
        if(action!=null)
            action.perform(token);
    }

    public interface IAction {
        void perform(String tokenFCM);
    }
}
