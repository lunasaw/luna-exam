# Spring boot 热加载



在实际的开发中避免不了自己测试的时候修修改改，甚至有些源代码的修改是需要重启项目的，这个时候热加载就帮了大忙了，其会自动将修改的代码应用到部署的项目中去，而不用自己再次的去手动重启，大大的提高了我们开发的效率，实现了代码随时改效果立马生效的效果，好了废话不多说了，下面来介绍怎解嵌入热加载的实现。

在pom文件中添加依赖（optional-->true表示覆盖父级项目中的引用）：

<dependency> 
 <groupId> org.springframework.boot </ groupId> 
 <artifactId> spring-boot-devtools </ artifactId> 
 <optional> true </ optional> 
 </ dependency>

就是这么简单，这样就可以了，当然了，有些时候我们再修改一些文件时是并不希望其触发重启的，例如一些静态资源等，此时我们可以设置一些排序路径来将其排除出去，在此之前先来介绍一下触发重启的条件吧：当DevTools监视类路径资源时，触发重新启动的唯一方法是更新类路径。导致类路径更新的方式取决于您正在使用的IDE。在Eclipse中，保存修改的文件将导致类路径被更新并触发重新启动。在IntelliJ IDEA中，构建project（Build -> Make Project）将具有相同的效果。

如果要自定义一些排除项，您可以使用该spring.devtools.restart.exclude属性。例如，仅排除 /static和/public你设置如下：
 spring.devtools.restart.exclude=static/**,public/**
 当您对不在类路径中的文件进行更改时，可能需要重新启动或重新加载应用程序。为此，请使用该 spring.devtools.restart.additional-paths属性配置其他路径来监视更改。

如果您不想使用重新启动功能，可以使用该spring.devtools.restart.enabled属性禁用它 。在大多数情况下，您可以将其设置为 application.properties（仍将初始化重新启动类加载器，但不会监视文件更改）。
 例如，如果您需要完全禁用重新启动支持，因为它不适用于特定库，则需要System在调用之前设置属性 SpringApplication.run(…​)。例如：
 public static void main(String[] args) {
   System.setProperty("spring.devtools.restart.enabled", "false");
   SpringApplication.run(MyApp.class, args);
 }

我不知道大家都使用的是什么IDE，我现在用的是IntelliJ IDEA，而这个工具有各特点就是编辑文件后其会立刻自动保存，并不需要手动的Ctrl+s来操作，造成的结果就是每当我修改一个触发启动的文件的时候其就会自动进行热加载，而我们并不想这样频繁的去热加载的话，可以进行一些特殊的设计实现仅在特定的时间去触发热加载：我们可以使用“触发文件”，这是一个特殊文件，当您要实际触发重新启动检查时，必须修改它。更改文件只会触发检查，只有在Devtools检测到它必须执行某些操作时才会重新启动。触发文件可以手动更新，也可以通过IDE插件更新。要使用触发器文件使用该spring.devtools.restart.trigger-file属性。