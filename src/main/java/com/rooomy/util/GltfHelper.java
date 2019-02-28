package com.rooomy.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;

import java.io.*;
import java.math.BigDecimal;

public class GltfHelper {
    private static String pactFile;
    // 默认除法运算精度

    private static final int DEFAULT_DIV_SCALE = 15;

    // 测试
    public static void main(String[] args) {
        String json = "null";
        try {
//			pactFile = "C:\\Users\\jin.tang\\Desktop\\亚马逊GLTF坐标轴需求\\非玻璃\\修正前\\B079TH27DN-orig\\gltf\\B079TH27DN.gltf";
            pactFile = "";
            json = readJsonData(pactFile);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        JSONObject dataJson = (JSONObject) JSONObject.parse(json, Feature.OrderedField);// 创建一个包含原始json串的json对象
        JSONArray features = dataJson.getJSONArray("nodes");// 找到features的json数组
        JSONObject jsonObject = features.getJSONObject(0);
        JSONObject addjsonObject = features.getJSONObject(2);
        JSONArray translation = jsonObject.getJSONArray("translation");// 找到features的json数组
        JSONArray scale = jsonObject.getJSONArray("scale");// 找到features的json数组
        JSONArray addJson = new JSONArray();
        addJson.add(0, translation.getDouble(0) / scale.getDoubleValue(0));
        addJson.add(1, translation.getDouble(1) / scale.getDoubleValue(1));
        addJson.add(2, translation.getDouble(2) / scale.getDoubleValue(2));
        translation.set(1, 0.0);
        addjsonObject.put("translation", addJson);
        String jsonString = dataJson.toJSONString();
        String format = JsonFormater.format(jsonString);
        outFile(format, pactFile);
        System.out.println(format);
    }

    public static void convertGltf(String pactFile) {
        String json = "null";
        try {
//			pactFile = "C:\\Users\\jin.tang\\Desktop\\亚马逊GLTF坐标轴需求\\非玻璃\\修正前\\B079TH27DN-orig\\gltf\\B079TH27DN.gltf";
            File file = new File(pactFile);
            File[] listFiles = file.listFiles(new FileFilter() {
                @Override
                public boolean accept(File pathname) {
                    String name = pathname.getName();
                    if (name.contains("gltf")) {
                        return true;
                    } else {
                        return false;
                    }
                }
            });
            for (File folderPath : listFiles) {
                if (folderPath.isDirectory()) {
                    File[] sherchFiles = folderPath.listFiles(new FileFilter() {
                        @Override
                        public boolean accept(File pathname) {
                            String gltfPath = pathname.getName();
                            if (gltfPath.endsWith(".gltf") || gltfPath.endsWith(".glb")) {
                                return true;
                            } else {
                                return false;
                            }
                        }
                    });
                    for (File f : sherchFiles) {
                        String name = f.getName();
                        if (name.endsWith(".gltf")) {
                            pactFile = f.getAbsolutePath();
                            json = readJsonData(pactFile);
                        } else if (name.endsWith(".glb")) {
                            f.delete();
                        }
                    }
                }
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        JSONObject dataJson = (JSONObject) JSONObject.parse(json, Feature.OrderedField);// 创建一个包含原始json串的json对象
        JSONArray features = dataJson.getJSONArray("nodes");// 找到features的json数组
        JSONObject jsonObject = features.getJSONObject(0);
        JSONObject addjsonObject = features.getJSONObject(2);
        JSONArray translation = jsonObject.getJSONArray("translation");// 找到features的json数组
        JSONArray scale = jsonObject.getJSONArray("scale");// 找到features的json数组
        JSONArray addJson = new JSONArray();
        double divide0 = divide(translation.getDouble(0), scale.getDoubleValue(0));
        if (divide0 > 0 && divide0 < 0.000001) {
            BigDecimal b = new BigDecimal(divide0);
            divide0 = b.setScale(6, BigDecimal.ROUND_HALF_UP).doubleValue();
        }
        addJson.add(0, divide0);
        double divide1 = divide(translation.getDouble(1), scale.getDoubleValue(1));
        if (divide1 > 0 && divide1 < 0.000001) {
            BigDecimal b = new BigDecimal(divide1);
            divide1 = b.setScale(6, BigDecimal.ROUND_HALF_UP).doubleValue();
        }
        addJson.add(1, divide1);
        double divide2 = divide(translation.getDouble(2), scale.getDoubleValue(2));
        if (divide2 > 0 && divide2 < 0.000001) {
            BigDecimal b = new BigDecimal(divide2);
            divide2 = b.setScale(6, BigDecimal.ROUND_HALF_UP).doubleValue();
        }
        addJson.add(2, divide2);
        translation.set(0, 0.0);
        translation.set(1, 0.0);
        translation.set(2, 0.0);
        addjsonObject.put("translation", addJson);
        String jsonString = dataJson.toJSONString();
        String format = JsonFormater.format(jsonString);
        outFile(format, pactFile);
        System.out.println(format);
    }

    public static void fileBoProcessing(String pactFile) {
        String json = "null";
        try {
//			pactFile = "C:\\Users\\jin.tang\\Desktop\\亚马逊GLTF坐标轴需求\\非玻璃\\修正前\\B079TH27DN-orig\\gltf\\B079TH27DN.gltf";
            File file = new File(pactFile);
            File[] listFiles = file.listFiles(new FileFilter() {

                @Override
                public boolean accept(File pathname) {
                    String name = pathname.getName();
                    if (name.contains("gltf")) {
                        return true;
                    } else {
                        return false;
                    }
                }
            });
            for (File folderPath : listFiles) {
                if (folderPath.isDirectory()) {
                    File[] sherchFiles = folderPath.listFiles(new FileFilter() {

                        @Override
                        public boolean accept(File pathname) {
                            String gltfPath = pathname.getName();
                            if (gltfPath.endsWith(".gltf") || gltfPath.endsWith(".glb")) {
                                return true;
                            } else {
                                return false;
                            }
                        }
                    });
                    for (File f : sherchFiles) {
                        String name = f.getName();
                        if (name.endsWith(".gltf")) {
                            pactFile = f.getAbsolutePath();
                            json = readJsonData(pactFile);
                        } else if (name.endsWith(".glb")) {
                            f.delete();
                        }
                    }
                }
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        JSONObject dataJson = (JSONObject) JSONObject.parse(json, Feature.OrderedField);// 创建一个包含原始json串的json对象
        JSONArray features = dataJson.getJSONArray("nodes");// 找到features的json数组
        JSONObject jsonObject = features.getJSONObject(0);
        JSONObject addjsonObject = features.getJSONObject(2);
        JSONObject addjsonObject1 = features.getJSONObject(4);
        JSONArray translation = jsonObject.getJSONArray("translation");// 找到features的json数组
        JSONArray scale = jsonObject.getJSONArray("scale");// 找到features的json数组
        JSONArray addJson = new JSONArray();
        double divide0 = divide(translation.getDouble(0), scale.getDoubleValue(0));
        if (divide0 > 0 && divide0 < 0.000001) {
            BigDecimal b = new BigDecimal(divide0);
            divide0 = b.setScale(6, BigDecimal.ROUND_HALF_UP).doubleValue();
        }
        addJson.add(0, divide0);
        double divide1 = divide(translation.getDouble(1), scale.getDoubleValue(1));
        if (divide1 > 0 && divide1 < 0.000001) {
            BigDecimal b = new BigDecimal(divide1);
            divide1 = b.setScale(6, BigDecimal.ROUND_HALF_UP).doubleValue();
        }
        addJson.add(1, divide1);
        double divide2 = divide(translation.getDouble(2), scale.getDoubleValue(2));
        if (divide2 > 0 && divide2 < 0.000001) {
            BigDecimal b = new BigDecimal(divide2);
            divide2 = b.setScale(6, BigDecimal.ROUND_HALF_UP).doubleValue();
        }
        addJson.add(2, divide2);
        translation.set(0, 0.0);
        translation.set(1, 0.0);
        translation.set(2, 0.0);
        addjsonObject.put("translation", addJson);
        addjsonObject1.put("translation", addJson);
        String jsonString = dataJson.toJSONString();
        String format = JsonFormater.format(jsonString);
        outFile(format, pactFile);
        System.out.println(format);
    }

    public static String readJsonData(String pactFile) throws IOException {
        // 读取文件数据
        // System.out.println("读取文件数据util");

        StringBuffer strbuffer = new StringBuffer();
        File myFile = new File(pactFile);// "D:"+File.separatorChar+"DStores.json"
        if (!myFile.exists()) {
            System.err.println("Can't Find " + pactFile);
        }
        try {
            FileInputStream fis = new FileInputStream(pactFile);
            InputStreamReader inputStreamReader = new InputStreamReader(fis, "UTF-8");
            BufferedReader in = new BufferedReader(inputStreamReader);

            String str;
            while ((str = in.readLine()) != null) {
                strbuffer.append(str); // new String(str,"UTF-8")
            }
            in.close();
        } catch (IOException e) {
            e.getStackTrace();
        }
        // System.out.println("读取文件结束util");
        return strbuffer.toString();
    }

    /**
     * 输出到文件
     */
    public static void outFile(String s, String path) {

        File file = new File(path);

        try (FileOutputStream fop = new FileOutputStream(file)) {

            // if file doesn't exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }

            // get the content in bytes
            byte[] contentInBytes = s.getBytes();

            fop.write(contentInBytes);
            fop.flush();
            fop.close();
            System.out.println("Done");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     *   * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到
     * <p>
     *   * 小数点以后10位，以后的数字四舍五入,舍入模式采用ROUND_HALF_EVEN
     * <p>
     *   * @param v1
     * <p>
     *   * @param v2
     * <p>
     *   * @return 两个参数的商
     * <p>
     *  
     */

    public static double divide(double v1, double v2) {
        return divide(v1, v2, DEFAULT_DIV_SCALE);
    }

    /**
     *    * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指
     * <p>
     *    * 定精度，以后的数字四舍五入。舍入模式采用ROUND_HALF_EVEN
     * <p>
     *    * @param v1
     * <p>
     *    * @param v2
     * <p>
     *    * @param scale 表示需要精确到小数点以后几位。
     * <p>
     *    * @return 两个参数的商
     * <p>
     *   
     */

    public static double divide(double v1, double v2, int scale) {

        return divide(v1, v2, scale, BigDecimal.ROUND_HALF_EVEN);

    }

    /**
     *    * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指
     * <p>
     *    * 定精度，以后的数字四舍五入。舍入模式采用用户指定舍入模式
     * <p>
     *    * @param v1
     * <p>
     *    * @param v2
     * <p>
     *    * @param scale 表示需要精确到小数点以后几位
     * <p>
     *    * @param round_mode 表示用户指定的舍入模式
     * <p>
     *    * @return 两个参数的商
     * <p>
     *   
     */
    public static double divide(double v1, double v2, int scale, int round_mode) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.divide(b2, scale, round_mode).doubleValue();
    }
}
