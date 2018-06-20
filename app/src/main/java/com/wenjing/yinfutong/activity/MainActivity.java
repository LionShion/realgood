package com.wenjing.yinfutong.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.wenjing.yinfutong.AppContext;
import com.wenjing.yinfutong.R;
import com.wenjing.yinfutong.base.BaseActivity;
import com.wenjing.yinfutong.utils.XmlParser;
import com.wenjing.yinfutong.widget.FragmentTabHost;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class MainActivity extends BaseActivity implements TabHost.OnTabChangeListener {
    private FragmentTabHost mTabHost;
    protected static ArrayList<TabItem> mModules;
    public static boolean isForeground = false;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onResume() {
        super.onResume();
        isForeground = true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        isForeground = false;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);

        initView();
        initTabs();
    }


    private void initView() {
        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
    }


    @Override
    public void onTabChanged(String tabId) {
        final int size = mTabHost.getTabWidget().getTabCount();

        for (int i = 0; i < size; i++) {
            View view = mTabHost.getTabWidget().getChildAt(i);
            if (view == mTabHost.getCurrentTabView()) {
                view.findViewById(R.id.tab_icon).setSelected(true);
                view.findViewById(R.id.tab_titile).setSelected(true);
            } else {
                view.findViewById(R.id.tab_icon).setSelected(false);
                view.findViewById(R.id.tab_titile).setSelected(false);
            }
        }
    }

    private void initTabs() {
        if (mModules == null) {
        } else {
            //直接清空，重新创建，不然切换语言无效
            mModules.clear();
        }
        mModules = new ArrayList<>();


        loadTabInfo();
        initTab();
    }


    private void initTab() {
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
        mTabHost.setCurrentTab(0);
        mTabHost.setOnTabChangedListener(this);
        int size = mModules.size();
        for (int i = 0; i < size; i++) {
            TabItem tabItem = mModules.get(i);
            TabHost.TabSpec tab = mTabHost.newTabSpec(tabItem.itemLabel);
            View indicator = getLayoutInflater().inflate(R.layout.tab_indicator, null);
            ImageView icon = indicator.findViewById(R.id.tab_icon);
            icon.setImageDrawable(tabItem.itemIcon);
            TextView title = indicator.findViewById(R.id.tab_titile);
            title.setText(tabItem.itemLabel);
            tab.setIndicator(indicator);
            tab.setContent(new TabHost.TabContentFactory() {
                public View createTabContent(String tag) {
                    return new View(MainActivity.this);
                }
            });
            mTabHost.getTabWidget().setDividerDrawable(null);
            mTabHost.addTab(tab, tabItem.itemTargetClass, null);
        }
    }

    private void loadTabInfo() {
        if (mModules.size() != 0) {
            return;
        }
        try {
            XmlParser xmlParser = new XmlParser(getResources().openRawResource(R.raw.main_tab_moudles));
            NodeList nodes = xmlParser.getRootElement().getElementsByTagName("Module");
            for (int i = 0; i < nodes.getLength(); i++) {
                Node node = nodes.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    TabItem module = new TabItem();
                    String type = element.getAttribute("type");
                    String label = element.getAttribute("lable");
                    String clsRef = element.getAttribute("class");
                    String iconRef = element.getAttribute("icon");

                    if (TextUtils.isEmpty(clsRef) || TextUtils.isEmpty(iconRef)) {
                        throw new IllegalArgumentException("The clsRef or iconRef in xml must have a validate value");
                    }
                    Class<?> cls = Object.class;
                    if (!clsRef.equals("null")) {
                        cls = TextUtils.isEmpty(clsRef) ? null : Class.forName(clsRef);
                    }

                    Drawable icon = getIcon(iconRef);
                    module.itemIcon = icon;
                    module.itemTargetClass = cls;
                    if (label.equals("@string/home")) {
                        module.itemLabel = getResources().getString(R.string.home);
                    }
                    if (label.equals("@string/mine")) {
                        module.itemLabel = getResources().getString(R.string.mine);
                    }
                    module.type = type;

                    mModules.add(module);
                }
            }

        } catch (Exception e) {
        }

    }

    private Drawable getIcon(String resName) {
        try {
            Field field = R.drawable.class.getField(resName);
            int resId = field.getInt(R.drawable.class);
            return getResources().getDrawable(resId);
        } catch (Exception e) {
        }

        return null;
    }

    public class TabItem {
        public Drawable itemIcon = null;
        public String itemLabel = null;
        public Class<?> itemTargetClass = null;
        public String type = "";

        @Override
        public boolean equals(Object o) {
            if (o == this) {
                return true;
            }
            if (o instanceof TabItem) {
                return this.itemTargetClass == null ? o == null : this.itemTargetClass.equals(((TabItem) o).itemTargetClass);
            }
            return false;
        }
    }

    long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                exitTime = System.currentTimeMillis();
                AppContext.showToast(getResources().getString(R.string.once_out_tip));
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
