//地图类，负责处理某一场景中Deadline与Block的交互

import java.awt.*;
import javax.swing.*;
import java.io.*;

public class Map extends JPanel{
    private int id;//地图编号
    private Deadline deadline;//戴德琳
    private Block[] block;//地图中物品 

    public Map(int mapID)
    {
        id=mapID;
        String mapFileName="./data/maps/map_"+String.format("%2d",id)+".txt";
        try(BufferedReader br=new BufferedReader(new FileReader(mapFileName)))
        {
            String line;
            while ((line = br.readLine()) != null) {
                // 解析每一行的键值对
                String[] parts = line.split("="); // 假设使用等号分隔键和值
                String variable = parts[0].trim(); // 变量名
                String value = parts[1].trim(); // 值
                switch(variable)
                {
                    case "deadlineX":
                        break;
                    case "deadlineY":
                        break;
                    case "blockID":
                        for(int i=0;i<4;i++)
                        {
                            line=br.readLine();
                            parts=line.split("=");
                            variable=parts[0].trim();
                            value=parts[1].trim();
                            switch(variable)
                            {
                                case "blockX":
                                    break;
                                case "blockY":
                                    break;
                                case "blockKind":
                                    break;
                                default:
                                    break;
                            }
                        }
                        break;
                    default:
                        break;
                }
            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
}
