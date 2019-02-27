package com.rooomy.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.rooomy.levy.convert.GltfToGlb;

import java.io.*;
import java.math.BigDecimal;
import java.util.List;

public class GltfConvertHelper {

    public static void process(String input) throws IOException {
        unzip(input);
        startConvert(input);
        zip(input);
    }

    private static void unzip(String input){

    }

    private static void zip(String input){

    }

    private static void startConvert(String input) throws IOException {
        List<String> gltfs = GltfToGlb.listGlftFiles(input);
        for(String gltf : gltfs){
            convertGltf(gltf);
            generateGlb(gltf);
        }
    }


    private static void convertGltf(String gltf) throws IOException {
        String json = readJsonData(gltf);
        JSONObject dataJson = (JSONObject) JSONObject.parse(json, Feature.OrderedField);// 创建一个包含原始json串的json对象
        JSONArray nodes = dataJson.getJSONArray("nodes");// 找到features的json数组
        JSONArray meshes = dataJson.getJSONArray("meshes");// 找到features的json数组
        JSONObject jsonObject = nodes.getJSONObject(0);
        JSONObject addjsonObject = nodes.getJSONObject(2);
        JSONObject addjsonObject1 = null;
        if (meshes.size() == 2){
            addjsonObject1 = nodes.getJSONObject(4);
        }
        JSONArray translation = jsonObject.getJSONArray("translation");// 找到features的json数组
        JSONArray scale = jsonObject.getJSONArray("scale");// 找到features的json数组
        JSONArray addJson = new JSONArray();
        double divide0 = GltfHelper.divide(translation.getDouble(0), scale.getDoubleValue(0));
        if (divide0 > 0 && divide0 < 0.000001) {
            BigDecimal b = new BigDecimal(divide0);
            divide0 = b.setScale(6, BigDecimal.ROUND_HALF_UP).doubleValue();
        }
        addJson.add(0, divide0);
        double divide1 = GltfHelper.divide(translation.getDouble(1), scale.getDoubleValue(1));
        if (divide1 > 0 && divide1 < 0.000001) {
            BigDecimal b = new BigDecimal(divide1);
            divide1 = b.setScale(6, BigDecimal.ROUND_HALF_UP).doubleValue();
        }
        addJson.add(1, divide1);
        double divide2 = GltfHelper.divide(translation.getDouble(2), scale.getDoubleValue(2));
        if (divide2 > 0 && divide2 < 0.000001) {
            BigDecimal b = new BigDecimal(divide2);
            divide2 = b.setScale(6, BigDecimal.ROUND_HALF_UP).doubleValue();
        }
        addJson.add(2, divide2);
        translation.set(0, 0.0);
        translation.set(1, 0.0);
        translation.set(2, 0.0);
        addjsonObject.put("translation", addJson);
        if (meshes.size() == 2){
            addjsonObject1.put("translation", addJson);
        }
        String jsonString = dataJson.toJSONString();
        String format = JsonFormater.format(jsonString);
        GltfHelper.outFile(format, gltf);
    }

    private static void generateGlb(String gltf) throws IOException {
        GltfToGlb.convert(gltf);
    }

    private static String readJsonData(String pactFile) throws IOException {
        StringBuilder strbuffer = new StringBuilder();
        File myFile = new File(pactFile);// "D:"+File.separatorChar+"DStores.json"
        if (!myFile.exists()) {
            System.err.println("Can't Find " + pactFile);
        }
        FileInputStream fis = new FileInputStream(pactFile);
        InputStreamReader inputStreamReader = new InputStreamReader(fis, "UTF-8");
        BufferedReader in = new BufferedReader(inputStreamReader);

        String str;
        while ((str = in.readLine()) != null) {
            strbuffer.append(str); // new String(str,"UTF-8")
        }
        in.close();
        return strbuffer.toString();
    }
}
