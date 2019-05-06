package com.example.luna.xml;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Xml;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.example.luna.R;
import com.example.luna.base.BaseActivity;
import com.example.luna.utils.HttpUtils;
import com.example.luna.utils.Logutils;
import okhttp3.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class InvoiceActivity extends BaseActivity {
    private static final String URL_07 = "https://zcyg001.com/lotteryV2/resultList.do?lotCode=BJSC&startDate=2018-10-07";
    private static final String URL_H = "https://zcyg001.com/lotteryV2/resultList.do?lotCode=BJSC";
    private static final String URL_B = "http://www.baidu.com/";
    private static final String URL_C = "http://www.cnblogs.com/";

    private static final String TAG = "MainActivityTAG";

    private ListView lv;
    private InvoiceAdapter mAdapter;
    List<NumberBean> beans = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice);
//        get();
        testJsoup();
        lv = findViewById(R.id.invoice_lv);
        mAdapter = new InvoiceAdapter(beans);
        lv.setAdapter(mAdapter);
    }

    public void testJsoup() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                try {
                    Document doc = Jsoup.connect(URL_07).get();

                   /* Elements elements = doc.select("div.post_item_body");

                    for (Element element : elements) {
                        Elements title = element.select("a.titlelnk");
                        Logutils.e(TAG, "title = " + title.get(0).text());
                        Logutils.e(TAG, "url = " + title.get(0).attr("href"));

                        Elements content = element.select("p.post_item_summary");
                        Logutils.e(TAG, "content = " + content.get(0).text());

                    }*/

//                    Elements elements = doc.select("div.post_item_body");
                    Elements elements = doc.select("div.rule-content");
                    Elements table = elements.select("table.awardList");
                    Elements tbody = table.select("tbody");
//                    Logutils.e(TAG, "tbody = " + tbody);

                    List<Integer> nums = new ArrayList<>();

                    for (Element element : tbody) {
                        Elements title = element.select("tr");
//                        String td = title.select("em").get(0).attr("class");
//                        String td1 = title.select("em").get(1).attr("class");
//                        Logutils.e(TAG, "td = " + td+",td1 = "+td1);
                        Elements tds = title.select("td");
                        for (Element td : tds) {
                            String number = td.text();
                            if (isInteger(number) && number.length() == 6) {
//                                Logutils.e(TAG, "time = " + td.text());
                                nums.add(Integer.parseInt(number));
                            }
                        }

                        Elements ems = title.select("em");
                        NumberBean bean = new NumberBean();
                        for (Element em : ems) {
                            String num = em.attr("class");
                            if (num.startsWith("pk10")) {
//                                Logutils.e(TAG, "em = " + num);
                                String real = num.substring(6, 8);
                                int realnum = Integer.parseInt(real);
                                setNum(bean, realnum);
                                if (bean.getTen() != 0) {
                                    beans.add(bean);
                                    bean = new NumberBean();
                                }
                            }
                        }
                    }
                    Logutils.e(TAG, "bean = " + beans.size() + ",nums = " + nums.size());
                    int count = Math.min(beans.size(), nums.size());

                    for (int i = 0; i < count; i++) {
                        beans.get(i).setNumber(nums.get(i));
                        Logutils.e(TAG, "bean = " + beans.get(i));
                    }
                    mAdapter.notifyDataSetChanged();

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }

    private void setNum(NumberBean bean, int realnum) {
        if (bean.getOne() == 0) {
            bean.setOne(realnum);
        } else if (bean.getTwo() == 0) {
            bean.setTwo(realnum);
        } else if (bean.getThree() == 0) {
            bean.setThree(realnum);
        } else if (bean.getFour() == 0) {
            bean.setFour(realnum);
        } else if (bean.getFive() == 0) {
            bean.setFive(realnum);
        } else if (bean.getSix() == 0) {
            bean.setSix(realnum);
        } else if (bean.getSeven() == 0) {
            bean.setSeven(realnum);
        } else if (bean.getEight() == 0) {
            bean.setEight(realnum);
        } else if (bean.getNine() == 0) {
            bean.setNine(realnum);
        } else if (bean.getTen() == 0) {
            bean.setTen(realnum);
        }
    }

    public boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }

    private void get() {
        OkHttpClient okHttpClient = new OkHttpClient();
        //2构造Request,
        //builder.get()代表的是get请求，url方法里面放的参数是一个网络地址
        Request.Builder builder = new Request.Builder();
        Request request = builder.get().url(URL_C).build();

        //3将Request封装成call
        Call call = okHttpClient.newCall(request);

        //4，执行call，这个方法是异步请求数据
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //失败调用
                Logutils.e(TAG, "onFailure: ");
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                //成功调用
                //获取网络访问返回的字符串
                String string = response.body().string();
//                Logutils.e(TAG, "onResponse: " + string);
//                string = string.replaceAll("&","&amp;");
//                string = string.replaceAll("<","&lt;");
//                string = string.replaceAll(">","&gt;");
//                parseXML(string);
                writeText(string);
            }
        });

    }

    /*public void readXMLDemo(String xml) throws Exception {

        Document document = DocumentHelper.parseText(xml); // 将字符串转为XML

        // 创建saxReader对象
        SAXReader reader = new SAXReader();
        // 通过read方法读取一个文件 转换成Document对象
//        Document document = reader.read(new File("src/car.xml"));
        //获取根节点元素对象
        Element node = document.getRootElement();
        //遍历所有的元素节点
        // listNodes(node);

        elementMethod(node);

    }

    public void elementMethod(Element node) {
        // 获取node节点中，子节点的元素名称为supercars的元素节点。
        Element e = node.element("head");
        // 获取supercars元素节点中，子节点为carname的元素节点(可以看到只能获取第一个carname元素节点)
        Element carname = e.element("title");

        Logutils.e(TAG,e.getName() + "----" + carname.getText());

        // 获取supercars这个元素节点 中，所有子节点名称为carname元素的节点 。

        List<Element> carnames = e.elements("carname");
        for (Element cname : carnames) {
            System.out.println(cname.getText());
        }

        // 获取supercars这个元素节点 所有元素的子节点。
        List<Element> elements = e.elements();

        for (Element el : elements) {
            System.out.println(el.getText());
        }

    }

    private void parseXML(String content) {
        XmlPullParser pullParser = Xml.newPullParser();
        try {

            InputStream is = new ByteArrayInputStream(content.getBytes());
            pullParser.setInput(is, "utf-8");
//            pullParser.setInput(new StringReader(content));
            int event = pullParser.getEventType();
            while (event != XmlPullParser.END_DOCUMENT) {
                switch (event) {
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:
                        Logutils.e(TAG, "START_TAG: " + pullParser.getName());

//                        if ("UpdateResult".equals(pullParser.getName())) {
//                            int count = pullParser.getAttributeCount();
//                            for (int i = 0; i < count; i++) {
//                                String key = pullParser.getAttributeName(i);
//                                if ("NeedUpdate".equals(key)) {
//                                    isNeedUpdate = "true".equals(pullParser.getAttributeValue(i));
//                                }
//                            }
//                        } else if ("FileUrl".equals(parser.getName())) {
//                            int count = parser.getAttributeCount();
//                            for (int i = 0; i < count; i++) {
//                                String key = parser.getAttributeName(i);
//                                if ("value".equals(key)) {
//                                    FileUrl = parser.getAttributeValue(i);
//                                }
//                            }
//                        }

                        break;
                    case XmlPullParser.END_TAG:
                        break;
                }
                safeNextText(pullParser);
                event = pullParser.next();
            }

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String safeNextText(XmlPullParser parser) {

        String result = null;

        try {
            result = parser.nextText();
            if (parser.getEventType() != XmlPullParser.END_TAG) {
                parser.nextTag();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }*/

    private void writeText(String content) {

        if (content == null || content.equals("")) {
            return;
        }

        String filePath = "/storage/emulated/0/eaglelog/";
        File dirFile = new File(filePath);
        if (!dirFile.exists()) {
            dirFile.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".txt";
        File file = new File(filePath + fileName);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            fos.write(content.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    class InvoiceAdapter extends BaseAdapter {

        private List<NumberBean> beans = new ArrayList<>();

        public InvoiceAdapter(List<NumberBean> beans) {
            this.beans.addAll(beans);
        }

        @Override
        public int getCount() {
            return beans.size();
        }

        @Override
        public Object getItem(int position) {
            return beans.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(mContext,R.layout.note_item,null);
                new ViewHolder(convertView);
            }
            ViewHolder holder = (ViewHolder) convertView.getTag();
            String bean = beans.get(position).toString();
            Logutils.e(TAG,"getView = "+bean);
            holder.tv.setText(bean);
            return convertView;
        }
    }

    class ViewHolder{
        TextView tv;
        public ViewHolder(View view){
            tv = view.findViewById(R.id.i_tv);
            view.setTag(this);
        }
    }

}
