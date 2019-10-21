package club.bluetroy.crawler.dao.entity;

/**
 * @author heyixin
 * Date: 2018-11-30
 * Time: 19:16
 */
public enum Category {
    /**
     * 当前最热
     */
    HOT("hot"),
    /**
     * 本月最热
     */
    TOP("top"),
    /**
     * 本月收藏
     */
    MONTH_TOP_COLLECT("tf"),
    /**
     * 收藏总量最多
     */
    TOP_COLLECT("mf"),
    /**
     * 最近得分
     */
    RECENT_POINT("rp"),

    /**
     * 十分钟以上
     */
    LONG("long"),

    /**
     * 最近加精
     */
    RECENT_BOUTIQUE("rf"),
    /**
     * 本月讨论
     */
    MONTH_DISCUSS("md");

    private String category;

    Category(String category) {
        this.category = category;
    }

    public String getCategory() {
        return this.category;
    }
}
