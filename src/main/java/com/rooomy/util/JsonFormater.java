package com.rooomy.util;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by chenyuzhi on 17-7-28.
 */
public class JsonFormater {
	private static boolean isDuo=false;
	private static boolean isMao=false;
	private static boolean isKuo=false;
    public static String format(String jsonStr){
        try {
            ByteArrayInputStream in = new ByteArrayInputStream(jsonStr.getBytes());
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            char ch;
            int read;
            int space=0;
            while((read = in.read()) > 0){
                ch = (char)read;
                switch (ch){
                    case '{': {
                        space = outputAndRightMove(space, ch, out);
                        break;
                    }
                    case '[': {
                        out.write(ch);
//                        outputNewline(out);
//                        out.write(getBlankingStringBytes(space));
                        space += 2;
                        isKuo=true;
                        if(isMao) {
                			isMao=false;
                		}
                        if(isDuo) {
                    		isDuo=false;
                        }
                        break;
                    }
                    case '}': {
                        space = outputAndLeftMove(space, ch, out);
                        if(isDuo) {
                    		isDuo=false;
                        }
                        if(isMao) {
                			isMao=false;
                		}
                        if(isKuo) {
                         	isKuo=false;
                         }
                        break;
                    }
                    case ']': {
                        space = outputAndLeftMove(space, ch, out);
                        if(isDuo) {
                    		isDuo=false;
                        }
                        if(isMao) {
                			isMao=false;
                		}
                        if(isKuo) {
                         	isKuo=false;
                         }
                        break;
                    }
                    case ',': {
                        out.write(ch);
                        outputNewline(out);
                        out.write(getBlankingStringBytes(space));
                        isDuo=true;
                        if(isMao) {
                			isMao=false;
                		}
                        if(isKuo) {
                         	isKuo=false;
                         }
                        break;
                    }case ':': {
                    	isMao=true;
                    	out.write(ch);
                    	out.write(getBlankingStringBytes(1));
                    	if(isDuo) {
                    		isDuo=false;
                        }
                    	if(isKuo) {
                         	isKuo=false;
                         }
                        break;
                    }
                    default: {
                        
                        if(isKuo) {
                        	outputNewline(out);
                        	out.write(getBlankingStringBytes(space));
                        	out.write(ch);
                        	isKuo=false;
                        }else {
                        	out.write(ch);
                        }
                        if(isDuo) {
                    		isDuo=false;
                        }
                        if(isMao) {
                			isMao=false;
                		}
                        if(isKuo) {
                         	isKuo=false;
                         }
                        break;
                    }
                }
            }
            return out.toString();
        } catch (IOException e){
            e.printStackTrace();
        }

        return null;
    }

    public static int outputAndRightMove(int space, char ch, ByteArrayOutputStream out) throws IOException {
        //换行
    	if(isDuo) {
    		isDuo=false;
    		 if(isKuo) {
             	outputNewline(out);
             	//向右缩进
        		out.write(getBlankingStringBytes(space));
             	isKuo=false;
             }
    	}else {
    		if(isMao) {
    			isMao=false;
    		}else {
    			if(isKuo) {
                 	isKuo=false;
                 }
    			outputNewline(out);
    			//向右缩进
    			out.write(getBlankingStringBytes(space));
    		}
    	}
        out.write(ch);
        outputNewline(out);
        space += 2;
        //再向右缩进多两个字符
        out.write(getBlankingStringBytes(space));
        return space;
    }
    public static int outputAndLeftMove(int space, char ch, ByteArrayOutputStream out) throws IOException{
        outputNewline(out);
        space -= 2;
        out.write(getBlankingStringBytes(space));
        out.write(ch);
        return space;
    }
    public static byte[] getBlankingStringBytes(int space){
        StringBuilder sb = new StringBuilder("");
        for (int i = 0; i < space; i++) {
            sb.append(" ");
        }
        return sb.toString().getBytes();
    }

    public static void outputNewline(ByteArrayOutputStream out){
        out.write('\r');
        out.write('\n');
    }
}
