package com.wenjing.yinfutong.base;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import com.wenjing.yinfutong.R;
import com.wenjing.yinfutong.retrofit.YinFuTongFactory;
import com.wenjing.yinfutong.retrofit.api.HomeApi;
import com.wenjing.yinfutong.retrofit.api.MineApi;
import com.wenjing.yinfutong.widget.DialogControl;
import com.wenjing.yinfutong.widget.dialog.WaitDialog;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import butterknife.ButterKnife;
import butterknife.Unbinder;


public abstract class BaseFragment extends Fragment implements View.OnClickListener {

    public static final HomeApi sHomeApi = YinFuTongFactory.getHomeApiSingleton();
    public static final MineApi sMineApi = YinFuTongFactory.getMineApiSingleton();

    protected static final int STATE_NONE = 0;//没有状态
    protected static final int STATE_REFRESH = 1;//刷新状态


    protected FragmentActivity curActivity;
    private Unbinder bind;

    protected boolean isVisible;

    private Method noteStateNotSavedMethod;
    private Object fragmentMgr;
    private String[] activityClassName = {"Activity", "FragmentActivity"};

    private QMUITipDialog mTipDialog;

    /**
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    protected void onVisible() {
    }


    protected void onInvisible() {
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    protected int getLayoutRes() {
        return 0;
    }

    protected abstract void initView(View view);


    @Override
    public void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        curActivity = getActivity();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        /*在fragmentActivity里重写onSaveInstanceState方法，但不做实现，也就是将super.onSaveInstanceState(outState)注释掉。
            当系统要回收Fragment时，我们告诉系统：不要再保存Fragment。
             相当于用户回到app的时候，我们就当用户是第一次打开app（因为很长时间没有操作了）。*/
        super.onSaveInstanceState(outState);
        invokeFragmentManagerNoteStateNotSaved();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = null;
        if (getLayoutRes() != 0) {
            view = inflater.inflate(getLayoutRes(), null, false);
        }
        //返回一个Unbinder值（进行解绑），注意这里的this不能使用getActivity()
        bind = ButterKnife.bind(this, view);
        initView(view);


        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                InputMethodManager manager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (getActivity().getCurrentFocus() != null && getActivity().getCurrentFocus().getWindowToken() != null) {
                        manager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    }
                }
                return false;
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    public void hideWaitDialog() {
        FragmentActivity activity = getActivity();
        if (activity instanceof DialogControl) {
            ((DialogControl) activity).hideWaitDialog();
        }
    }

    public WaitDialog showWaitDialog() {
        return showWaitDialog(R.string.loading);
    }

    protected WaitDialog showDialog(int resid) {
        return showWaitDialog(resid);
    }

    protected WaitDialog showWaitDialog(int resid) {
        FragmentActivity activity = getActivity();
        if (activity instanceof DialogControl) {
            return ((DialogControl) activity).showWaitDialog(resid);
        }
        return null;
    }

    public FragmentActivity getCurActivity() {
        if (isAdded()) {
            return getActivity();
        } else {
            return curActivity;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();

    }

    protected String getUITag() {
        return "MainScreen";
    }

    protected TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            onAfterTextChanged(s);
        }
    };

    protected void onAfterTextChanged(Editable s) {

    }

    public boolean onBackPressed() {
        return false;
    }

    @Override
    public void onClick(View v) {

    }

    public boolean dispatchKeyEvent(KeyEvent event) {
        return false;
    }

    public void net() {

    }

    private void invokeFragmentManagerNoteStateNotSaved() {
        //java.lang.IllegalStateException: Can not perform this action after onSaveInstanceState
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            return;
        }
        try {
            if (noteStateNotSavedMethod != null && fragmentMgr != null) {
                noteStateNotSavedMethod.invoke(fragmentMgr);
                return;
            }
            Class cls = getClass();
            do {
                cls = cls.getSuperclass();
            } while (!(activityClassName[0].equals(cls.getSimpleName())
                    || activityClassName[1].equals(cls.getSimpleName())));

            Field fragmentMgrField = prepareField(cls, "mFragments");
            if (fragmentMgrField != null) {
                fragmentMgr = fragmentMgrField.get(this);
                noteStateNotSavedMethod = getDeclaredMethod(fragmentMgr, "noteStateNotSaved");
                if (noteStateNotSavedMethod != null) {
                    noteStateNotSavedMethod.invoke(fragmentMgr);
                }
            }

        } catch (Exception ex) {
        }
    }

    private Field prepareField(Class<?> c, String fieldName) throws NoSuchFieldException {
        while (c != null) {
            try {
                Field f = c.getDeclaredField(fieldName);
                f.setAccessible(true);
                return f;
            } finally {
                c = c.getSuperclass();
            }
        }
        throw new NoSuchFieldException();
    }

    private Method getDeclaredMethod(Object object, String methodName, Class<?>... parameterTypes) {
        Method method = null;
        for (Class<?> clazz = object.getClass(); clazz != Object.class; clazz = clazz.getSuperclass()) {
            try {
                method = clazz.getDeclaredMethod(methodName, parameterTypes);
                return method;
            } catch (Exception e) {
            }
        }
        return null;
    }

    protected void showDialog() {
        QMUITipDialog.Builder waitDialog = new QMUITipDialog.Builder(getContext());
        waitDialog.setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING);
        mTipDialog = waitDialog.create();
        mTipDialog.setCancelable(true);
        mTipDialog.show();

    }

    protected QMUITipDialog getmTipDialog(){
        return mTipDialog;
    }

}
