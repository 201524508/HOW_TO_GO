package algo;

/**
 * Created by YUUUUU on 2017-06-05.
 */

public class myStack {
    private int visited[];
    private int size;
    private int top;

    public myStack(int n){
        visited = new int[n];
        for (int i = 0; i < n; i++) {
            visited[i] = -1;
        }
        size = n;
        top = -1;
    }

    public int getSize(){
        return size;
    }

    public int[] getArray(){
        return visited;
    }

    public boolean isFull(){
        if(top == size - 1){
            return true;
        }
        return false;
    }

    public void push(int label){
        if (isFull()) {
            //cout << "stack is full!\n";
            return;
        }
        else {
            visited[++top] = label;
        }
    }

    public boolean isIn(int l) {
        boolean result = false;
        for (int i = 0; i < size; i++) {
            if (visited[i] == l) {
                result = true;
            }
        }
        return result;
    }
}
