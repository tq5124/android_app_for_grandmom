# 详细设计文档4.	编程协定	94.1	操作系统	9
本小组为三人小组，一人使用 mac os 10.9，三人使用 window74.2	编程工具	9
小组统一开发工具是eclipse，安装android develop tool，集成了编程工具、调试、联接、编译、模拟仿真、诊断、检测等功能。
统一编码格式为utf8，换行采用unix格式6.	逻辑结构	10
6.1	常量定义	10
无6.2	界面元素定义	10

public static final class array {
        public static final int gpsFrequency_array=0x7f050000;
        public static final int gpsFrequency_array_value=0x7f050001;
    }
    public static final class attr {
    }
    public static final class dimen {
        public static final int activity_horizontal_margin=0x7f060000;
        public static final int activity_vertical_margin=0x7f060001;
    }
    public static final class drawable {
        public static final int camera=0x7f020000;
        public static final int cog=0x7f020001;
        public static final int envelope=0x7f020002;
        public static final int ic_launcher=0x7f020003;
        public static final int phone=0x7f020004;
        public static final int user=0x7f020005;
    }
    public static final class id {
        public static final int action_settings=0x7f0a0029;
        public static final int add_contact=0x7f0a0000;
        public static final int contact_list=0x7f0a0025;
        public static final int contact_name=0x7f0a0023;
        public static final int contact_number=0x7f0a0024;
        public static final int contact_photo=0x7f0a0022;
        public static final int container=0x7f0a0002;
        public static final int imageView1=0x7f0a000d;
        public static final int main_btn_back=0x7f0a0027;
        public static final int main_btn_home=0x7f0a0028;
        public static final int main_clock=0x7f0a0004;
        public static final int main_date=0x7f0a0005;
        public static final int main_message=0x7f0a0009;
        public static final int main_phone=0x7f0a0008;
        public static final int main_photo=0x7f0a000b;
        public static final int main_settings=0x7f0a000c;
        public static final int main_show=0x7f0a0026;
        public static final int main_time_title=0x7f0a0003;
        public static final int message_btn_read=0x7f0a000f;
        public static final int message_btn_send=0x7f0a000e;
        public static final int message_btn_sendOther=0x7f0a0018;
        public static final int message_btn_sendSaved1=0x7f0a0016;
        public static final int message_btn_sendSaved2=0x7f0a0017;
        public static final int message_btn_toOther=0x7f0a001b;
        public static final int message_btn_toSaved1=0x7f0a0019;
        public static final int message_btn_toSaved2=0x7f0a001a;
        public static final int message_confirm_no=0x7f0a0015;
        public static final int message_confirm_what_context=0x7f0a0013;
        public static final int message_confirm_what_title=0x7f0a0012;
        public static final int message_confirm_who_context=0x7f0a0011;
        public static final int message_confirm_who_title=0x7f0a0010;
        public static final int message_confirm_yes=0x7f0a0014;
        public static final int phone_call_other_btn=0x7f0a001f;
        public static final int phone_call_other_title=0x7f0a001e;
        public static final int phone_list=0x7f0a001d;
        public static final int phone_title=0x7f0a001c;
        public static final int photo_new=0x7f0a0020;
        public static final int photo_old=0x7f0a0021;
        public static final int store_contact_list=0x7f0a0001;
        public static final int tableLayout1=0x7f0a0006;
        public static final int tableRow1=0x7f0a0007;
        public static final int tableRow2=0x7f0a000a;
    }
    public static final class layout {
        public static final int activity_contact=0x7f030000;
        public static final int activity_main=0x7f030001;
        public static final int activity_message=0x7f030002;
        public static final int activity_message_confirm=0x7f030003;
        public static final int activity_message_text=0x7f030004;
        public static final int activity_message_who=0x7f030005;
        public static final int activity_phone=0x7f030006;
        public static final int activity_photo=0x7f030007;
        public static final int contact_confirm=0x7f030008;
        public static final int contact_item=0x7f030009;
        public static final int contact_list=0x7f03000a;
        public static final int fragment_bottom=0x7f03000b;
        public static final int fragment_main=0x7f03000c;
    }
    public static final class menu {
        public static final int main=0x7f090000;
    }
    public static final class string {
        public static final int action_settings=0x7f070002;
        public static final int app_name=0x7f070000;
        public static final int hello_world=0x7f070001;
        public static final int mostUsedPerson=0x7f070003;
    }
    public static final class style {
        public static final int Main_btn=0x7f080002;
    }
    public static final class xml {
        public static final int preference_message=0x7f040000;
        public static final int preferences_phone=0x7f040001;
        public static final int preferences_settings=0x7f040002;
    }6.3	类文件结构定义	10
参见`tq-类文件结构`文件夹的图片，全部放进来即可7.	源文件列表	11
|---.classpath
|---.DS_Store
|---.project
|---.settings
|        |---org.eclipse.jdt.core.prefs
|---AndroidManifest.xml
|---assets
|---bin
|    |---AndroidManifest.xml
|    |---classes
|    |      |---com
|    |      |    |---example
|    |      |    |      |---forgrandmon
|    |      |    |      |          |---BuildConfig.class
|    |      |    |      |          |---Contact.class
|    |      |    |      |          |---ContactActivity$1.class
|    |      |    |      |          |---ContactActivity$2.class
|    |      |    |      |          |---ContactActivity.class
|    |      |    |      |          |---ContactListActivity$1ContactItemListener$1.class
|    |      |    |      |          |---ContactListActivity$1ContactItemListener$2.class
|    |      |    |      |          |---ContactListActivity$1ContactItemListener.class
|    |      |    |      |          |---ContactListActivity.class
|    |      |    |      |          |---ContactModel.class
|    |      |    |      |          |---Contacts.class
|    |      |    |      |          |---ContactsAdapter.class
|    |      |    |      |          |---DB_Helper.class
|    |      |    |      |          |---MainActivity$Buttom_share.class
|    |      |    |      |          |---MainActivity$PlaceholderFragment.class
|    |      |    |      |          |---MainActivity.class
|    |      |    |      |          |---MessageActivity.class
|    |      |    |      |          |---PhoneActivity$1.class
|    |      |    |      |          |---PhoneActivity.class
|    |      |    |      |          |---PhotoActivity.class
|    |      |    |      |          |---R$array.class
|    |      |    |      |          |---R$attr.class
|    |      |    |      |          |---R$dimen.class
|    |      |    |      |          |---R$drawable.class
|    |      |    |      |          |---R$id.class
|    |      |    |      |          |---R$layout.class
|    |      |    |      |          |---R$menu.class
|    |      |    |      |          |---R$string.class
|    |      |    |      |          |---R$style.class
|    |      |    |      |          |---R$xml.class
|    |      |    |      |          |---R.class
|    |      |    |      |          |---SettingsPreference.class
|    |---classes.dex
|    |---dexedLibs
|    |        |---android-support-v4-368c7dc10d5990272315d8b6e5824527.jar
|    |---forGrandmon.apk
|    |---jarlist.cache
|    |---R.txt
|    |---res
|    |    |---crunch
|    |    |     |---drawable
|    |    |     |       |---camera.png
|    |    |     |       |---cog.png
|    |    |     |       |---envelope.png
|    |    |     |       |---phone.png
|    |    |     |       |---user.png
|    |    |     |---drawable-hdpi
|    |    |     |            |---ic_launcher.png
|    |    |     |---drawable-mdpi
|    |    |     |            |---ic_launcher.png
|    |    |     |---drawable-xhdpi
|    |    |     |             |---ic_launcher.png
|    |    |     |---drawable-xxhdpi
|    |    |     |              |---ic_launcher.png
|    |---resources.ap_
|---gen
|    |---com
|    |    |---example
|    |    |      |---forgrandmon
|    |    |      |          |---BuildConfig.java
|    |    |      |          |---R.java
|---ic_launcher-web.png
|---libs
|    |---android-support-v4.jar
|---proguard-project.txt
|---project.properties
|---res
|    |---drawable
|    |       |---.DS_Store
|    |       |---camera.png
|    |       |---cog.png
|    |       |---envelope.png
|    |       |---phone.png
|    |       |---user.png
|    |---drawable-hdpi
|    |            |---ic_launcher.png
|    |---drawable-ldpi
|    |---drawable-mdpi
|    |            |---ic_launcher.png
|    |---drawable-xhdpi
|    |             |---ic_launcher.png
|    |---drawable-xxhdpi
|    |              |---ic_launcher.png
|    |---layout
|    |     |---activity_contact.xml
|    |     |---activity_main.xml
|    |     |---activity_message.xml
|    |     |---activity_message_confirm.xml
|    |     |---activity_message_text.xml
|    |     |---activity_message_who.xml
|    |     |---activity_phone.xml
|    |     |---activity_photo.xml
|    |     |---contact_confirm.xml
|    |     |---contact_item.xml
|    |     |---contact_list.xml
|    |     |---fragment_bottom.xml
|    |     |---fragment_main.xml
|    |---menu
|    |    |---main.xml
|    |---values
|    |     |---array.xml
|    |     |---dimens.xml
|    |     |---strings.xml
|    |     |---styles.xml
|    |---values-v11
|    |         |---styles.xml
|    |---values-v14
|    |         |---styles.xml
|    |---values-w820dp
|    |            |---dimens.xml
|    |---xml
|    |    |---preference_message.xml
|    |    |---preferences_phone.xml
|    |    |---preferences_settings.xml
|---src
|    |---com
|    |    |---example
|    |    |      |---forgrandmon
|    |    |      |          |---Contact.java
|    |    |      |          |---ContactActivity.java
|    |    |      |          |---ContactListActivity.java
|    |    |      |          |---ContactModel.java
|    |    |      |          |---Contacts.java
|    |    |      |          |---ContactsAdapter.java
|    |    |      |          |---DB_Helper.java
|    |    |      |          |---MainActivity.java
|    |    |      |          |---MessageActivity.java
|    |    |      |          |---PhoneActivity.java
|    |    |      |          |---PhotoActivity.java
|    |    |      |          |---SettingsPreference.java

4.使用说明	64.1安装和初始化	6使用apk安装，要求设备运行在android 4.0以上的系统4.4出错和恢复	8
重启应用即可8.程序文件（或命令文件）和数据文件一览表	9
本应用只有一个apk文件，其他不暴露给用户。如需查看源码的文件列表请参看详细设计文档9.用户操作举例	9
1. 拨打电话
2. 发送短信
3. 查看收到的短信
4. 拍摄照片
5. 查看照片
6. 登录网站查询GPS信息
7. 设置程序首选项