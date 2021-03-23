package redis.struct;

import lombok.extern.slf4j.Slf4j;

/**
 * @author qianniu
 * @date 2020/11/16 10:08 下午
 * @desc
 */
@Slf4j
public class SimpleDynamicString {

    private int len;

    private int free;

    private char[] buff;

    public SimpleDynamicString(char[] buff) {
        this.len = buff.length;
        this.free = 0;
        this.buff = buff;
    }

    public int length(){
        return this.len;
    }

    public SimpleDynamicString removeFreeSpace(){
        if(free == 0){
            return this;
        }

        char[] buff = new char[len];
        System.arraycopy(this.buff,0,buff,0,len);
        this.buff = buff;
        this.free = 0;
        return this;
    }

    public SimpleDynamicString append(char[] buff){
        int len = this.len;
        int free = this.free;
        if(buff.length > free){
            resize(buff.length);
        }
        for(int i= 0; i< buff.length; i++){
            this.buff[i+len] = buff[i];
        }

        this.len = len + buff.length;
        this.free = this.buff.length - this.len;
        return this;
    }

    private void resize(int length) {
        int newLen = len + length;
        char[] newBuff;
        if(newLen > 1024 * 1024){
            newBuff = new char[1024 * 1024 + newLen];
        }else {
            newBuff = new char[newLen * 2];
        }
        System.arraycopy(buff,0,newBuff,0,buff.length);

        this.buff = newBuff;
        log.debug("长度从：{} 扩容到：{}",this.len,this.buff.length);
    }

    public String toString(){
        log.debug("buffer length = {},len = {}, free ={}",buff.length,len,free);
        char[] chars = new char[this.len];
        for(int i=0; i< this.len; i++){
            chars[i] = this.buff[i];
        }
        return String.valueOf(chars);
    }

    public static void main(String[] args) {
        char[] chars = {'a','b'};
        SimpleDynamicString simpleDynamicString = new SimpleDynamicString(chars);
        SimpleDynamicString append = simpleDynamicString.append(new char[]{'c', 'd'});
        append.toString();

        append.removeFreeSpace();
        append.toString();
    }

}
