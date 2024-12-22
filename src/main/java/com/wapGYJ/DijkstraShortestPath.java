package com.wapGYJ;

import java.util.*;

public class DijkstraShortestPath {

    static final int INF = Integer.MAX_VALUE;
    static Map<Character, String> buildingNames = new HashMap<>();
    static Map<String, Character> reverseBuildingNames = new HashMap<>();


    private static String getBuildingName(int index) {
        // 用于将建筑物索引映射到具体建筑物名称（需要根据实际情况修改）
        String[] names = {
                "数字图书馆", "第二教学楼", "中心食堂", "信科大楼", "老图书馆", "宁静苑二舍"
        };
        return names[index];
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            int n = scanner.nextInt();  // 建筑物个数
            int m = scanner.nextInt();  // 路径条数
            if (n == 0 && m == 0) {
                break;
            }

            // 读取建筑物名称
            char[] buildings = new char[n];
            for (int i = 0; i < n; i++) {
                buildings[i] = scanner.next().charAt(0);
            }

            // 设置建筑物与名称的映射
            buildingNames.clear();
            reverseBuildingNames.clear();
            for (int i = 0; i < n; i++) {
                buildingNames.put(buildings[i], getBuildingName(i));
                reverseBuildingNames.put(getBuildingName(i), buildings[i]);
            }

            // 初始化邻接矩阵
            int[][] graph = new int[n][n];
            for (int i = 0; i < n; i++) {
                Arrays.fill(graph[i], INF);
            }
            for (int i = 0; i < n; i++) {
                graph[i][i] = 0;  // 自己到自己是0
            }

            // 读取路径
            for (int i = 0; i < m; i++) {
                char a = scanner.next().charAt(0);
                char b = scanner.next().charAt(0);
                int d = scanner.nextInt();
                int aIndex = Arrays.binarySearch(buildings, a);
                int bIndex = Arrays.binarySearch(buildings, b);
                graph[aIndex][bIndex] = Math.min(graph[aIndex][bIndex], d);
                graph[bIndex][aIndex] = Math.min(graph[bIndex][aIndex], d);  // 无向图
            }

            // 获取起点和终点
            char start = scanner.next().charAt(0);
            char end = scanner.next().charAt(0);

            // 调用Dijkstra算法
            int startIndex = Arrays.binarySearch(buildings, start);
            int endIndex = Arrays.binarySearch(buildings, end);
            dijkstra(graph, n, startIndex, endIndex, buildings);
        }
        scanner.close();
    }

    private static void dijkstra(int[][] graph, int n, int start, int end, char[] buildings) {
        int[] dist = new int[n];  // 最短距离
        int[] prev = new int[n];  // 前驱节点
        boolean[] visited = new boolean[n];

        Arrays.fill(dist, INF);
        dist[start] = 0;
        Arrays.fill(prev, -1);

        for (int i = 0; i < n; i++) {
            int u = -1;
            for (int j = 0; j < n; j++) {
                if (!visited[j] && (u == -1 || dist[j] < dist[u])) {
                    u = j;
                }
            }

            if (dist[u] == INF) break;  // 如果剩下的节点无法到达

            visited[u] = true;

            for (int v = 0; v < n; v++) {
                if (graph[u][v] != INF && dist[u] + graph[u][v] < dist[v]) {
                    dist[v] = dist[u] + graph[u][v];
                    prev[v] = u;
                }
            }
        }

        // 打印结果
        if (dist[end] == INF) {
            System.out.println("No path");
            return;
        }

        // 路径回溯
        List<String> path = new ArrayList<>();
        for (int at = end; at != -1; at = prev[at]) {
            path.add(String.valueOf(reverseBuildingNames.get(buildingNames.get(buildings[at]))));
        }
        Collections.reverse(path);

        // 输出最短路径长度
        System.out.println(dist[end]);

        // 输出路径的字母形式
        System.out.println(String.join(" ----> ", path));

        // 输出建筑物名和字母名的映射
        for (int i = 0; i < buildings.length; i++) {
            System.out.print(buildings[i] + "：" + buildingNames.get(buildings[i]) + " ");
        }
        System.out.println();

        // 输出路径的建筑物名
        System.out.println(String.join(" ----> ", path));

        // 汇总显示
        System.out.println(String.join("到", path) + "的最短距离为" + dist[end] + "米");
    }


}
