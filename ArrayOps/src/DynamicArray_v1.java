
public class DynamicArray_v1<T> {

    public int _jInitialSize = 4;
    public int _jIncrementBy = 2;

    public int _jCurrentFilled = 0;
    private T[] _arr = null;

    public DynamicArray_v1(int jInitialSize, int jIncrementBy) {
        initialize(jInitialSize, jIncrementBy);
    }

    public DynamicArray_v1() {
        initialize(_jInitialSize, _jIncrementBy);
    }

    private void initialize(int jInitialSize, int jIncrementBy) {
        _jInitialSize = jInitialSize;
        _jIncrementBy = jIncrementBy;
        _arr = (T[]) new Object[jInitialSize];

    }

    public void append(T tObj) {
        if (_jCurrentFilled >= _arr.length) {
            int jNewSize = _arr.length + _jIncrementBy;
            T[] arrTNew = (T[]) new Object[jNewSize];
            System.arraycopy(_arr, 0, arrTNew, 0, _arr.length);
            _arr = arrTNew;
        }
        _arr[_jCurrentFilled] = tObj;
        _jCurrentFilled++;
    }

    public T getAt(int index) {
        if (index >= _jCurrentFilled) {
            throw new RuntimeException("Index out of bounds");
        }
        return _arr[index];
    }

    public void setAt(int index, T tObj) {
        if (index >= _jCurrentFilled) {
            throw new RuntimeException("Index out of bounds");
        }
        _arr[index] = tObj;
    }

    public void removeAt(int index) {
        if (index >= _jCurrentFilled) {
            throw new RuntimeException("Index out of bounds");
        }
        System.arraycopy(_arr, index + 1, _arr, index, _jCurrentFilled - index);
        _jCurrentFilled--;
    }

    public int getCount() {
        return _jCurrentFilled;
    }
}
