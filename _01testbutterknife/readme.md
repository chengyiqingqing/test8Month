##### 以前的话经常使用findViewById()来绑定布局中ID，这样写不仅麻烦而且还没有什么效率，今天推荐一款由JakeWharton大神写的框架Butterknife用来绑定ID。



#### 导入依赖

既然是是一个第三方的开发库，让先让我们把导入项目中：
**Module**的build.gradle文件中:



```bash
apply plugin: 'com.jakewharton.butterknife'
```



```bash
dependencies {
	// 配套androidx使用
 compile 'com.jakewharton:butterknife:10.0.0'
 annotationProcessor 'com.jakewharton:butterknife-compiler:10.0.0'
}
```

**Project**的build.gradle文件中：

```bash
buildscript {
  dependencies {
    classpath 'com.jakewharton:butterknife-gradle-plugin:10.0.0'
  }
}
```

这里需要特殊说明一下，如果按照[官方网站](https://github.com/JakeWharton/butterknife)上面的方式导入的话，会报这个错误（需要升级你的Gradle插件）：
*To use the DSL implementation() you have to use:*
所以只用按照我上面的方式导入即可。



#### 若报错1：

Could not find org.jetbrains.trove4j:trove4j:20160824.  可添加如下依赖解决

```
buildscript {
    repositories {
        maven{ url'http://maven.aliyun.com/nexus/content/groups/public/' }
        maven{ url'http://maven.aliyun.com/nexus/content/repositories/jcenter'}   
    }
}


allprojects {
    repositories {
        maven{ url'http://maven.aliyun.com/nexus/content/groups/public/' }
        maven{ url'http://maven.aliyun.com/nexus/content/repositories/jcenter'}  
    }
}
```

#### 若报错2：

The given artifact contains a string literal with a package reference 'android.support.v4.content' that cannot be safely rewritten.

```cpp
    //更新ButterKnife
    implementation "com.jakewharton:butterknife:10.0.0"
    kapt "com.jakewharton:butterknife-compiler:10.0.0"
```

#### 若报错3：

Caused by: com.android.tools.r8.utils.AbortException: Error: Static interface methods are only supported starting with Android N (--min-api 24): void butterknife.Unbinder.lambda

```undefined
android {
		// java8支持下
    compileOptions {
        sourceCompatibility JavaVersion.VERSION_1_8
        targetCompatibility JavaVersion.VERSION_1_8
    }
}
```

#### 使用方法

- 绑定View



```

    @BindView(R.id.et_butter)
    EditText et_butter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_butter);
        //首先将ButterKnife注入,这行代码要在setContentView()之后执行。
        ButterKnife.bind(this);
        tv_butter.setText("绑定TextView");
        et_butter.setText("绑定EditText");
    }
```

- 绑定事件



```css
@OnClick(R.id.btn_butter)
    public void onBtnClick(View view) {
        Toast.makeText(this, "btn_butter被点击了", Toast.LENGTH_SHORT).show();
    }
```

- 绑定资源



```css
 @BindString(R.string.app_name)
    String app_name;
 @BindColor(R.color.colorAccent)
    int colorAccent;
```

还有很多各种场景下的绑定，其实套路都一样，这里主要举例三个应用场景。

#### Zelezny插件

如果你像我一样懒的连这些代码都不想写的话，下面推荐一款**Zelezny插件**（Android Studio），这个插件会自动在布局中查找控件的id，然后直接绑定，你说爽不爽。



总结：



最后聊几个问题：



参考文档：

https://www.jianshu.com/p/9b12867a42c4

