package DataCollection;

public final class Info {
    public long timestamp;
    public String type;
    public java.util.List<String> tags;
    public String params;

    public Info() {
        this.timestamp = (long) 0;
        this.type = "";
        this.tags = null;
        this.params = "";
    }

    public Info(long timestamp, String type, java.util.List<String> tags, String params) {
        this.timestamp = timestamp;
        this.type = type;
        this.tags = tags;
        this.params = params;
    }

    public static final class Holder {
        public Holder() {
        }

        public Holder(Info value) {
            this.value = value;
        }

        public Info value;
    }

    public static void __write(Common.OputStream __oput, Info __obj) {
        if (__obj == null) __obj = new Info();
        __oput.write(__obj.timestamp);
        __oput.write(__obj.type);
        Common.StrVec.__write(__oput, __obj.tags);
        __oput.write(__obj.params);
    }

    public static Info __read(Common.IputStream __iput) throws Common.Exception {
        Info __obj = new Info();
        __obj.timestamp = __iput.readLong();
        __obj.type = __iput.readString();
        __obj.tags = Common.StrVec.__read(__iput);
        __obj.params = __iput.readString();
        return __obj;
    }

    public static void __textWrite(Common.OputStream __oput, String __name, Info __obj) {
        if (__obj == null) return;
        __oput.textStart(__name);
        __oput.textWrite("timestamp", __obj.timestamp);
        __oput.textWrite("type", __obj.type);
        Common.StrVec.__textWrite(__oput, "tags", __obj.tags);
        __oput.textWrite("params", __obj.params);
        __oput.textEnd();
    }

    public static Info __textRead(Common.IputStream __iput, String __name, int __idx) {
        if (!__iput.textStart(__name, __idx)) return null;
        Info __obj = new Info();
        __obj.timestamp = __iput.textReadLong("timestamp", 0, (long) 0);
        __obj.type = __iput.textReadString("type", 0, "");
        __obj.tags = Common.StrVec.__textRead(__iput, "tags", 0);
        __obj.params = __iput.textReadString("params", 0, "");
        __iput.textEnd();
        return __obj;
    }
}
