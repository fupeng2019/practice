package com.example.demo.extend;

public class HandlerDataSource {

    /**
     * 使用本地线程空间，避免并发问题
     */
    private static ThreadLocal<String> handlerThredLocal = new ThreadLocal<String>();

    /**
     * @desction: 提供给AOP去设置当前的线程的数据源的信息
     * @param: datasource
     * @return: void
     */
    public static void putDataSource(String datasource) {
        handlerThredLocal.set(datasource);
    }

    /**
     * @desction: 提供给AbstractRoutingDataSource的实现类，通过key选择数据源
     * @return: java.lang.String
     */
    public static String getDataSource() {
        return handlerThredLocal.get();
    }

    /**
     * @desction: 使用默认的数据源
     */
    public static void clear() {
        handlerThredLocal.remove();
    }

}
