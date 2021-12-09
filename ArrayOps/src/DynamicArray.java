

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author gadre
 */
public class DynamicArray<T> {

    public int _jInitialSize = 64;

    public int _jCurrentFilled = 0;
    private T[][] _arr = null;

    public DynamicArray(int jInitialSize) {
        initialize(jInitialSize);
    }

    public DynamicArray() {
        initialize(_jInitialSize);
    }

    private void initialize(int jInitialSize) {
        _jInitialSize = jInitialSize;
        _arr = (T[][]) new Object[1][];
        _arr[0] = (T[]) new Object[_jInitialSize];

    }

    public void append(T tObj) {
        if (_jCurrentFilled >= _arr.length * _jInitialSize) {
            int jNewSize = _arr.length + 1;
            T[][] arrTNew = (T[][]) new Object[jNewSize][];
            System.arraycopy(_arr, 0, arrTNew, 0, _arr.length);
            _arr = arrTNew;
            _arr[_arr.length - 1] = (T[]) new Object[_jInitialSize];
        }
        int jArray = _jCurrentFilled / _jInitialSize;
        int jElement = _jCurrentFilled % _jInitialSize;
        _arr[jArray][jElement] = tObj;
        _jCurrentFilled++;
    }

    public T getAt(int index) {
        if (index >= _jCurrentFilled) {
            throw new RuntimeException("Index out of bounds");
        }
        int jArray = index / _jInitialSize;
        int jElement = index % _jInitialSize;
        return _arr[jArray][jElement];
    }

    public void setAt(int index, T tObj) {
        if (index >= _jCurrentFilled) {
            throw new RuntimeException("Index out of bounds");
        }
        int jArray = index / _jInitialSize;
        int jElement = index % _jInitialSize;
        _arr[jArray][jElement] = tObj;
    }

    public void removeAt(int index) {
        if (index >= _jCurrentFilled) {
            throw new RuntimeException("Index out of bounds");
        }
        int jArray = index / _jInitialSize;
        int jElement = index % _jInitialSize;
        System.arraycopy(_arr[jArray], jElement + 1, _arr[jArray], jElement, _arr[jArray].length - jElement - 1);
        int jCurArray;
        for (jCurArray = jArray + 1; jCurArray < _arr.length; jCurArray++) {
            T tAt0 = _arr[jCurArray][0];
            _arr[jCurArray - 1][_jInitialSize - 1] = tAt0;
            System.arraycopy(_arr[jCurArray], 1, _arr[jCurArray], 0, _arr[jCurArray].length - 1);
        }
        _jCurrentFilled--;
    }

    public int getCount() {
        return _jCurrentFilled;
    }
}
