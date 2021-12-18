import java.util.Arrays;

// 普通变量存其变量名和 type, 通过变量名索引
// struct 和 function 的变量名和其 type 中的 name 相同
public class SymbolTable {

    public static final int HASH_TABLE_SIZE = 0x3fff;

    private final Symbol[] table;

    public SymbolTable() {
        this.table = new Symbol[SymbolTable.HASH_TABLE_SIZE];
        Arrays.fill(this.table, null);
    }

    private int getIndex(String name) {
        int val = 0, i;
        for (char c : name.toCharArray()) {
            val = (val << 2) + (int) c;
            // HASH_TABLE_SIZE 描述了符号表的⼤⼩
            if ((i = (val & ~HASH_TABLE_SIZE)) != 0) {
                val = (val ^ (i >> 12)) & HASH_TABLE_SIZE;
            }
        }
        return val;
    }

    public void put(Symbol n) {
        int index = this.getIndex(n.getName());
        if (this.table[index] != null) {
            Symbol cur = this.table[index];
            while (cur.hasNext()) cur = cur.getNext();
            cur.setNext(n);
            return;
        }
        this.table[index] = n;
    }

    public Symbol get(String name) {
        return this.table[this.getIndex(name)];
    }

    public boolean contains(String name) {
        return this.table[this.getIndex(name)] != null;
    }

}
