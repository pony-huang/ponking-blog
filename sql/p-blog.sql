/*
Navicat MySQL Data Transfer

Source Server         : DESKTOP-RD3SK04_MYSQL
Source Server Version : 80016
Source Host           : localhost:3306
Source Database       : p-blog

Target Server Type    : MYSQL
Target Server Version : 80016
File Encoding         : 65001

Date: 2020-10-07 22:53:10
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for blog_article
-- ----------------------------
DROP TABLE IF EXISTS `blog_article`;
CREATE TABLE `blog_article` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `title` varchar(200) NOT NULL DEFAULT '' COMMENT '文章标题',
  `summary` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '文章摘要',
  `image` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '文章的预览图片',
  `content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci COMMENT '文章内容',
  `content_md` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci COMMENT ' Markdown格式的文章内容',
  `category_id` bigint(20) DEFAULT NULL COMMENT '分类ID',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '发表时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后更新时间',
  `top` tinyint(4) DEFAULT '0' COMMENT '文章是否置顶  0：否  1：是',
  `commented` tinyint(4) NOT NULL DEFAULT '1' COMMENT '是否开启评论 0：关闭 1：开启',
  `original` tinyint(4) NOT NULL DEFAULT '1' COMMENT '是否为原创文章 0：转载 1：原创',
  `source_url` varchar(200) DEFAULT NULL COMMENT '原文链接，转载文章才需填写',
  `visits` int(11) NOT NULL DEFAULT '0' COMMENT '访问量',
  `status` tinyint(4) NOT NULL COMMENT '状态 (0：草稿 1：已发布 2：回收站 )',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_title` (`title`) COMMENT '文章标题唯一索引'
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8 COMMENT='文章表';

-- ----------------------------
-- Records of blog_article
-- ----------------------------
INSERT INTO `blog_article` VALUES ('1', '1030 Travel Plan(深度优先搜索)', '测试测试测试', 'https://ponking-blog.oss-cn-beijing.aliyuncs.com/2020/09/02/17ea2393b8b7407f94fb9969bf576984.jpg', '<h1><a id=\"Hello_World_0\"></a>Hello World</h1>\n<p>你好，世界。<br />\n欢迎使用本博客系统。<br />\n写博客需要坚持，一起努力加油吧！</p>\n', '# 1030 Travel Plan(深度优先搜索)\n\nA traveler\'s map gives the distances between cities along the highways, together with the cost of each highway. Now you are supposed to write a program to help a traveler to decide the shortest path between his/her starting city and the destination. If such a shortest path is not unique, you are supposed to output the one with the minimum cost, which is guaranteed to be unique.\n\n### Input Specification:\n\nEach input file contains one test case. Each case starts with a line containing 4 positive integers *N*, *M*, *S*, and *D*, where *N* (≤500) is the number of cities (and hence the cities are numbered from 0 to *N*−1); *M* is the number of highways; *S* and *D* are the starting and the destination cities, respectively. Then *M* lines follow, each provides the information of a highway, in the format:\n\n```\nCity1 City2 Distance Cost\n```\n\n### Output Specification:\n\nFor each test case, print in one line the cities along the shortest path from the starting point to the destination, followed by the total distance and the total cost of the path. The numbers must be separated by a space and there must be no extra space at the end of output.\n\n### Sample Input:\n\n``` in\n4 5 0 3\n0 1 1 20\n1 3 2 30\n0 3 4 10\n0 2 2 20\n2 3 1 20\n```\n\n### Sample Output:\n\n``` out\n0 2 3 3 40\n```\n\n题意:选出一条最短路径(若路径相等,选出花费成本最低的路径)\n\n分析:明显DFS算法为该题最优解(Dijkatra都可以),计算获得最短路径和最低成本.\n\n代码:\n\n``` c++\n#include <iostream>\n#include<vector>\n#define MAX 99999999\n\n\nusing namespace std;\nvector<int> path;\nstruct Travel {\n	int dis = -1, money;\n};\n\nint mincosts = MAX, mindis = MAX;\n\nint n, m, s, d;\nvoid dfs(vector<vector<Travel> > &graph, vector<bool> &mark, vector<int> &tpath, int start, int destination, int dis, int costs) {\n	mark[start] = true;\n	tpath.push_back(start);\n	if (start == destination) {\n		if (dis < mindis) {\n			mincosts = costs;\n			mindis = dis;\n			path = tpath;\n		}\n		else if (dis == mindis && costs < mincosts) {\n			mincosts = costs;\n			path = tpath;\n		}\n	}\n	else {\n		for (int i = 0; i < n; i++) {\n			if (graph[start][i].dis != -1 && mark[i] != true) {\n				dfs(graph, mark, tpath, i, destination, dis + graph[start][i].dis, costs + graph[start][i].money);\n			}\n		}\n	}\n	mark[start] = false;\n	tpath.pop_back();\n}\n\n\nint main()\n{\n	\n	cin >> n >> m >> s >> d;\n	vector<vector<Travel> > graph(n, vector<Travel>(n));\n	vector<bool> mark(n, false);\n	vector<int> tpath;\n	int a, b;\n	for (int i = 0; i < m; i++) {\n		cin >> a >> b;\n		cin >> graph[a][b].dis >> graph[a][b].money;\n		graph[b][a] = graph[a][b];\n	}\n	dfs(graph, mark, tpath, s, d, 0, 0);\n	for (int i = 0; i < path.size(); i++)  cout << path[i] << \" \";\n	cout << mindis << \" \" << mincosts << endl;\n	return 0;\n}\n```', '1', '2018-12-21 00:00:00', '2018-11-25 15:37:31', '1', '1', '1', 'http://www.baidu.com', '13', '1');
INSERT INTO `blog_article` VALUES ('2', '1075 链表元素分类 (25 分)', '给定一个单链表，请编写程序将链表元素进行分类排列，使得所有负值元素都排在非负值元素的前面，而 [0, K] 区间内的元素都排在大于 K 的元素前面。但每一类内部元素的顺序是不能改变的。例如：给定链表为 18→7→-4→0→5→-6→10→11→-2，K 为 10，则输出应该为 -4→-6→-2→7→0→5→10→18→11。', 'https://ponking-blog.oss-cn-beijing.aliyuncs.com/2020/09/07/5ca245159cd54063861ff3b8f3219344.jpg', '<p style=\"text-align: center;\">\r\n	<span style=\"font-size: 14pt;\">欢迎来到<strong>我的个人博客</strong></span>\r\n</p>\r\n<div class=\"alert alert-danger\">我是一个好人。<br>我真的是一个好人哦！\r\n</div>\r\n<div class=\"alert alert-success\">这是个自定义文章示例界面<br>内容完全自定义<br>嘿嘿嘿~</div>\r\n<div class=\"alert alert-danger\">博客项目在<a href=\"https://github.com/iszhouhua/blog\">这里https://github.com/iszhouhua/blog</a><br>参考的博客主题在<a href=\"https://github.com/ZEROKISEKI/hexo-theme-gal\">这里https://github.com/ZEROKISEKI/hexo-theme-gal</a><br>参考网站在<a href=\"https://www.mmgal.com/\">这里https://www.mmgal.com/</a></div>', '# 1075 链表元素分类 (25 分)\n\n给定一个单链表，请编写程序将链表元素进行分类排列，使得所有负值元素都排在非负值元素的前面，而 [0, K] 区间内的元素都排在大于 K 的元素前面。但每一类内部元素的顺序是不能改变的。例如：给定链表为 18→7→-4→0→5→-6→10→11→-2，K 为 10，则输出应该为 -4→-6→-2→7→0→5→10→18→11。\n\n### 输入格式：\n\n每个输入包含一个测试用例。每个测试用例第 1 行给出：第 1 个结点的地址；结点总个数，即正整数N (≤105)；以及正整数K (≤103)。结点的地址是 5 位非负整数，NULL 地址用 −1 表示。\n\n接下来有 N 行，每行格式为：\n\n```\nAddress Data Next\n```\n\n其中 `Address` 是结点地址；`Data` 是该结点保存的数据，为 [−105,105] 区间内的整数；`Next` 是下一结点的地址。题目保证给出的链表不为空。\n\n### 输出格式：\n\n对每个测试用例，按链表从头到尾的顺序输出重排后的结果链表，其上每个结点占一行，格式与输入相同。\n\n### 输入样例：\n\n```in\n00100 9 10\n23333 10 27777\n00000 0 99999\n00100 18 12309\n68237 -6 23333\n33218 -4 00000\n48652 -2 -1\n99999 5 68237\n27777 11 48652\n12309 7 33218\n```\n\n### 输出样例：\n\n```out\n33218 -4 68237\n68237 -6 48652\n48652 -2 12309\n12309 7 00000\n00000 0 99999\n99999 5 23333\n23333 10 00100\n00100 18 27777\n27777 11 -1\n```\n\n代码如下:\n\n```c++\n// PAT1073乙级.cpp : 此文件包含 \"main\" 函数。程序执行将在此处开始并结束。\n//\n\n#include <iostream>\n#include<vector>\n#include<stdio.h>\n#include<iomanip>\nusing namespace std;\n\nstruct node {\n	int data, next;\n}list[100000];\n\nint main()\n{\n	int n, start, k;\n	vector<int> v[3];\n	cin >> start >> n >> k;\n	for (int i = 0; i < n; i++) {\n		int a, b, c;\n		cin >> a >> b >> c;\n		list[a].data = b;\n		list[a].next = c;\n	}\n	int p = start;\n	while (p != -1) {\n		if (list[p].data < 0) {\n			v[0].push_back(p);\n		}\n		else if (list[p].data >= 0&&list[p].data<=k) {\n			v[1].push_back(p);\n		}\n		else {\n			v[2].push_back(p);\n		}\n		p = list[p].next;\n	}\n	int flag = 1;\n	for (int i = 0; i < 3; i++) {\n		for (int j = 0; j < v[i].size(); j++) {\n			if (flag == 1) {\n                printf(\"%05d %d \", v[i][j], list[v[i][j]].data);\n                flag = 0;\n            } else {\n                printf(\"%05d\\n%05d %d \", v[i][j], v[i][j], list[v[i][j]].data);\n            }\n		}\n	}\n	cout << \"-1\";\n	return 0;\n}\n\n```', '2', '2018-12-15 03:03:43', '2020-03-06 03:03:43', '0', '1', '0', null, '8', '1');
INSERT INTO `blog_article` VALUES ('3', '1037 Magic Coupon (25 分)', 'The magic shop in Mars is offering some magic coupons. Each coupon has an integer *N* printed on it, meaning that when you use this coupon with a product, you may get *N* times the value of that product back! What is more, the shop also offers some bonus product for free. However, if you apply a coupon with a positive *N* to this bonus product, you will have to pay the shop *N* times the value of the bonus product... but hey, magically, they have some coupons with negative *N*\'s!.....', 'https://ponking-blog.oss-cn-beijing.aliyuncs.com/2020/09/07/f5fcd32a02dc4ff48d220d2f8a9f3709.jpg', null, '# 1037 Magic Coupon (25 分)\n\nThe magic shop in Mars is offering some magic coupons. Each coupon has an integer *N* printed on it, meaning that when you use this coupon with a product, you may get *N* times the value of that product back! What is more, the shop also offers some bonus product for free. However, if you apply a coupon with a positive *N* to this bonus product, you will have to pay the shop *N* times the value of the bonus product... but hey, magically, they have some coupons with negative *N*\'s!\n\nFor example, given a set of coupons { 1 2 4 −1 }, and a set of product values { 7 6 −2 −3 } (in Mars dollars M$) where a negative value corresponds to a bonus product. You can apply coupon 3 (with *N* being 4) to product 1 (with value M$7) to get M$28 back; coupon 2 to product 2 to get M$12 back; and coupon 4 to product 4 to get M$3 back. On the other hand, if you apply coupon 3 to product 4, you will have to pay M$12 to the shop.\n\nEach coupon and each product may be selected at most once. Your task is to get as much money back as possible.\n\n### Input Specification:\n\nEach input file contains one test case. For each case, the first line contains the number of coupons *N**C*, followed by a line with *N**C* coupon integers. Then the next line contains the number of products *N**P*, followed by a line with *N**P* product values. Here 1≤*N**C*,*N**P*≤105, and it is guaranteed that all the numbers will not exceed 230.\n\n### Output Specification:\n\nFor each test case, simply print in a line the maximum amount of money you can get back.\n\n### Sample Input:\n\n```in\n4\n1 2 4 -1\n4\n7 6 -2 -3\n```\n\n### Sample Output:\n\n```out\n43\n```\n\n分析:将货物与优惠卷的数据分别降序排序(负数部分基于第一次排序升序排序),之后遍历两边数据的乘积是否为正数,若是,与sum相加;注意:负数与负数的乘积为正数,当乘积为负数时,只要将另外一边数据遍历就行.\n\n```c++\n#include <iostream>\n#include<algorithm>\n#include <vector>\nusing namespace std;\nbool cmp(int a,int b) {\n	if (a < 0 && b < 0) return a < b;\n	else return a > b;\n}\nint main()\n{\n	vector<int> coupon, product;\n	int n ,a;\n	cin >> n;\n	for (int i = 0; i < n; i++) {\n		cin >> a;\n		coupon.push_back(a);\n	}\n	cin >> n;\n	for (int i = 0; i < n; i++) {\n		cin >> a;\n		product.push_back(a);\n	}\n	sort(coupon.begin(), coupon.end(), cmp);\n	sort(product.begin(), product.end(), cmp);\n	int len1 = coupon.size(), len2 = product.size(),sum=0;\n	for (int i = 0, j = 0; i < coupon.size() && j < product.size(); j++, i++) {\n		if (coupon[i] * product[j] > 0) sum += coupon[i] * product[j];\n		else if (coupon[i] > 0 && product[j] < 0 ) {\n			while (i<len1) {\n				if (coupon[i] * product[j] > 0) {\n					sum += coupon[i] * product[j];\n					break;\n				}\n				i++;\n			}\n		}\n		else if (coupon[i] < 0 && product[j] > 0 ) {\n			while (j<len2) {\n				if (coupon[i] * product[j] > 0) {\n					sum += coupon[i] * product[j];\n					break;\n				}\n				j++;\n			}\n		}\n	}\n	cout << sum;\n}\n```\n', '2', '2020-03-18 12:20:20', '2020-03-18 12:20:20', '0', '0', '1', null, '2', '1');
INSERT INTO `blog_article` VALUES ('4', '1065 A+B and C (64bit) (20 分)', 'Given three integers *A*, *B* and *C* in [−263,263], you are supposed to tell whether *A*+*B*>*C*.', 'https://ponking-blog.oss-cn-beijing.aliyuncs.com/2020/09/02/17ea2393b8b7407f94fb9969bf576984.jpg', null, '\n\n# 65 A+B and C (64bit) (20 分)\n\nGiven three integers *A*, *B* and *C* in [−263,263], you are supposed to tell whether *A*+*B*>*C*.\n\n### Input Specification:\n\nThe first line of the input gives the positive number of test cases, *T* (≤10). Then *T* test cases follow, each consists of a single line containing three integers *A*, *B* and *C*, separated by single spaces.\n\n### Output Specification:\n\nFor each test case, output in one line `Case #X: true` if *A*+*B*>*C*, or `Case #X: false` otherwise, where *X* is the case number (starting from 1).\n\n### Sample Input:\n\n```in\n3\n1 2 3\n2 3 4\n9223372036854775807 -9223372036854775808 0\n```\n\n### Sample Output:\n\n```out\nCase #1: false\nCase #2: true\nCase #3: false\n```\n\n分析:(当时我用大数据相加法,题目20分数,顿时傻眼了,看了别人代码,原来可以用溢出判断就行)\n\n数值范围整数[- 2^63,2^63]\n\n当a > 0且b > 0时,若sum = a + b < 0 ,溢出;当a < 0,b < 0时,sum(**范围值:0~2*2^63**)有可能溢出 ;当a>0(b>0)且b<0(a>0) 不可能溢出.\n\n```c++\n#include<iostream>\n#include<stdio.h>\nusing namespace std;\nint main(){\n    long long a,b,c;\n    int n,id=1;\n    cin>>n;\n    while(n--){\n        cin>>a>>b>>c;\n        long long sum=a+b;\n        if(a>0&&b>0&&sum<0) printf(\"Case #%d: true\\n\",id++);\n        else if(a<0&&b<0&&sum>=0) printf(\"Case #%d: false\\n\",id++);\n        else if(sum>c) printf(\"Case #%d: true\\n\",id++);\n        else printf(\"Case #%d: false\\n\",id++);\n    }\n    return 0;\n}\n```', '2', '2020-03-18 20:20:38', '2020-03-18 20:20:38', '0', '1', '0', null, '2', '1');
INSERT INTO `blog_article` VALUES ('5', 'Digital Library', 'A Digital Library contains millions of books, stored according to their titles, authors, key words of their abstracts, publishers, and published years. Each book is assigned an unique 7-digit number as its ID. Given any query from a reader, you are supposed to output the resulting books, sorted in increasing order of their ID\'s.', 'https://ponking-blog.oss-cn-beijing.aliyuncs.com/2020/09/02/17ea2393b8b7407f94fb9969bf576984.jpg', null, '#  Digital Library\n\nA Digital Library contains millions of books, stored according to their titles, authors, key words of their abstracts, publishers, and published years. Each book is assigned an unique 7-digit number as its ID. Given any query from a reader, you are supposed to output the resulting books, sorted in increasing order of their ID\'s.\n\n### Input Specification:\n\nEach input file contains one test case. For each case, the first line contains a positive integer *N* (≤10^4) which is the total number of books. Then *N* blocks follow, each contains the information of a book in 6 lines:\n\n- Line #1: the 7-digit ID number;\n\n- Line #1: the 7-digit ID number;\n- Line #2: the book title -- a string of no more than 80 characters;\n- Line #3: the author -- a string of no more than 80 characters;\n- Line #4: the key words -- each word is a string of no more than 10 characters without any white space, and the keywords are separated by exactly one space;\n- Line #5: the publisher -- a string of no more than 80 characters;\n- Line #6: the published year -- a 4-digit number which is in the range [1000, 3000].\n\nIt is assumed that each book belongs to one author only, and contains no more than 5 key words; there are no more than 1000 distinct key words in total; and there are no more than 1000 distinct publishers.\n\nAfter the book information, there is a line containing a positive integer *M* (≤1000) which is the number of user\'s search queries. Then *M* lines follow, each in one of the formats shown below:\n\n- 1: a book title\n- 2: name of an author\n- 3: a key word\n- 4: name of a publisher\n- 5: a 4-digit number representing the year\n\n### Output Specification:\n\nFor each query, first print the original query in a line, then output the resulting book ID\'s in increasing order, each occupying a line. If no book is found, print `Not Found` instead.\n\n### Sample Input:\n\n```in\n3\n1111111\nThe Testing Book\nYue Chen\ntest code debug sort keywords\nZUCS Print\n2011\n3333333\nAnother Testing Book\nYue Chen\ntest code sort keywords\nZUCS Print2\n2012\n2222222\nThe Testing Book\nCYLL\nkeywords debug book\nZUCS Print2\n2011\n6\n1: The Testing Book\n2: Yue Chen\n3: keywords\n4: ZUCS Print\n5: 2011\n3: blablabla\n```\n\n### Sample Output:\n\n```out\n1: The Testing Book\n1111111\n2222222\n2: Yue Chen\n1111111\n3333333\n3: keywords\n1111111\n2222222\n3333333\n4: ZUCS Print\n1111111\n5: 2011\n1111111\n2222222\n3: blablabla\nNot Found\n```\n\n题意:\n\n**分析：1、对除了id之外的其他信息都建立一个map<string, set>，把相应的id插入对应搜索词的map的集合里面，形成一个信息对应一个集合，集合里面是复合条件的书的id**\n\n**2、因为对于输入的关键词（可以重复，算是书本对应的tag标签吧～）没有给定关键词的个数，可以使用while(cin >> s)并且判断c = getchar()，c是否等于\\n，如果是再退出循环～**\n\n**3、建立query，通过传参的形式可以将不同的map名称统一化，先要判断map.find()和m.end()是否相等，如果不等再去遍历整个map，输出所有满足条件的id，如果相等就说明不存在这个搜索词对应的id，那么就要输出Not Found～**\n\n\n\n\n\n\n```c++\n#include<iostream>\n#include<stdio.h>\n#include<map>\n#include<set>\n\nusing namespace std;\n\nmap<string ,set<int> > title,author,key,pub,year;\n\nvoid query(map<string ,set<int> > &m,string &str){\n    set<int>::iterator it;\n    if(m.find(str)!=m.end()){\n            it=m[str].begin();\n            while(it!=m[str].end()){\n                printf(\"%07d\\n\", *it);\n                it++;\n            }\n    }else\n      puts(\"Not Found\");\n}\n\nint main(){\n    int n,m,id,num;\n    scanf(\"%d\",&n);\n    string ttitle,tauthor,tkey,tpub,tyear;\n    for(int i=0;i<n;i++){\n        scanf(\"%d\\n\",&id);\n        getline(cin,ttitle);\n        title[ttitle].insert(id);\n        getline(cin,tauthor);\n        author[tauthor].insert(id);\n        while(cin>>tkey){\n            key[tkey].insert(id);\n            char c=getchar();\n            if(c==\'\\n\') break;\n        }\n        getline(cin,tpub);\n        pub[tpub].insert(id);\n        getline(cin,tyear);\n        year[tyear].insert(id);\n    }\n    scanf(\"%d\",&m);\n    for(int i=0;i<m;i++){\n        scanf(\"%d: \",&num);\n        string temp;\n        getline(cin,temp);\n       cout << num << \": \" << temp << \"\\n\";\n        if(num==1) query(title,temp);\n        else if(num==2) query(author,temp);\n        else if(num==3) query(key,temp);\n        else if(num==4) query(pub,temp);\n        else if(num==5) query(year ,temp);\n    }\n    return 0;\n}\n```', '7', '2020-03-19 15:03:00', '2020-03-19 15:03:00', '0', '1', '1', null, '1', '1');
INSERT INTO `blog_article` VALUES ('9', '1004 成绩排名 (20 分)', '读入 n（>0）名学生的姓名、学号、成绩，分别输出成绩最高和成绩最低学生的姓名和学号。', 'https://ponking-blog.oss-cn-beijing.aliyuncs.com/2020/09/07/48709154400a43149d2f3435e3c7378e.png', null, '\n\n## 1004 成绩排名 (20 分)\n\n读入 *n*（>0）名学生的姓名、学号、成绩，分别输出成绩最高和成绩最低学生的姓名和学号。\n\n\n### 输入格式：\n\n每个测试输入包含 1 个测试用例，格式为\n\n```\n第 1 行：正整数 n\n第 2 行：第 1 个学生的姓名 学号 成绩\n第 3 行：第 2 个学生的姓名 学号 成绩\n  ... ... ...\n第 n+1 行：第 n 个学生的姓名 学号 成绩\n```\n\n其中`姓名`和`学号`均为不超过 10 个字符的字符串，成绩为 0 到 100 之间的一个整数，这里保证在一组测试用例中没有两个学生的成绩是相同的。\n\n### 输出格式：\n\n对每个测试用例输出 2 行，第 1 行是成绩最高学生的姓名和学号，第 2 行是成绩最低学生的姓名和学号，字符串间有 1 空格。\n\n### 输入样例：\n\n```in\n3\nJoe Math990112 89\nMike CS991301 100\nMary EE990830 95\n```\n\n### 输出样例：\n\n```out\nMike CS991301\nJoe Math990112\n```\n\n**题意:输入n个学生信息(姓名,学号,成绩),找出他们之中成绩最高和最低成绩,并依次输出他们姓名 ,学号.**\n\n**分析:先定义stu结构体(name,num,grade),先将下标为0的学生的成绩赋值给max,min,然后将n-1个学生逐一比较,用maxindex和minindex记录max,min所对应的学生.**\n\n\n\n代码如下:\n\n```c++\n#include<iostream>\nusing namespace std;\n\nstruct stu {\n	char name[11];\n	char num[11];\n	int grade;\n}stu[1000];\n\n\n\nint main() {\n	int n, i, max, min, maxindex, minindex;\n	while (cin >> n) {\n		for (i = 0; i < n; i++) {\n			cin >> stu[i].name >> stu[i].num >> stu[i].grade;\n		}\n		max = stu[0].grade; maxindex = 0;\n		min = stu[0].grade; minindex = 0;\n		for (i = 1; i < n; i++) {\n			if (stu[i].grade > max) {\n				max = stu[i].grade;\n				maxindex = i;\n			}\n			if (stu[i].grade < min) {\n				minindex = i;\n				min = stu[i].grade;\n			}\n		}\n		cout << stu[maxindex].name << \" \" << stu[maxindex].num << endl;\n		cout << stu[minindex].name << \" \" << stu[minindex].num <<endl;\n\n	}\n}\n```', '2', '2020-04-04 14:55:54', '2019-07-14 14:55:54', '0', '0', '1', '', '1', '1');
INSERT INTO `blog_article` VALUES ('10', '1041 Be Unique (散列)(20 分)', 'Being unique is so important to people on Mars that even their lottery is designed in a unique way. The rule of winning is simple: one bets on a number chosen from [1,104]. The first one who bets on a unique number wins. For example, if there are 7 people betting on { 5 31 5 88 67 88 17 }, then the second one who bets on 31 wins.', 'https://ponking-blog.oss-cn-beijing.aliyuncs.com/2020/09/02/17ea2393b8b7407f94fb9969bf576984.jpg', null, '\n## 1041 Be Unique (散列)(20 分)\n\nBeing unique is so important to people on Mars that even their lottery is designed in a unique way. The rule of winning is simple: one bets on a number chosen from [1,104]. The first one who bets on a unique number wins. For example, if there are 7 people betting on { 5 31 5 88 67 88 17 }, then the second one who bets on 31 wins.\n\n<!--more-->\n\n### Input Specification:\n\nEach input file contains one test case. Each case contains a line which begins with a positive integer *N* (≤105) and then followed by *N* bets. The numbers are separated by a space.\n\n### Output Specification:\n\nFor each test case, print the winning number in a line. If there is no winner, print `None` instead.\n\n### Sample Input 1:\n\n```in\n7 5 31 5 88 67 88 17\n```\n\n### Sample Output 1:\n\n```out\n31\n```\n\n### Sample Input 2:\n\n```in\n5 888 666 666 888 888\n```\n\n### Sample Output 2:\n\n```out\nNone\n```\n\n题意:查找第一个出现一次的数字;若不存在,输出None.(英语菜,这题非常简单,可惜理解错误,竟然做了一小时,我也服了自己)\n\n```c++\n#include<iostream>\n#include<stdio.h>\n\nusing namespace std;\nint main() {\n    int a[100001]={0},m[10001];\n    int n,index;\n    cin>>n;\n    for(int i=0;i<n;i++){\n        cin>>a[i];\n        m[a[i]]++;\n    }\n    for(int i=0;i<n;i++){\n        if(m[a[i]]==1){\n            cout<<a[i];\n            return 0;\n        }\n    }\n    cout<<\"None\";\n	return 0;\n}\n\n```\n\n', '2', '2020-04-04 22:57:11', '2020-01-01 22:57:11', '1', '0', '1', '', '0', '1');
INSERT INTO `blog_article` VALUES ('11', '1021 Deepest Root (25 分)', 'A graph which is connected and acyclic can be considered a tree. The height of the tree depends on the selected root. Now you are supposed to find the root that results in a highest tree. Such a root is called the deepest root.', 'https://ponking-blog.oss-cn-beijing.aliyuncs.com/2020/09/07/62633c52d65b43718f1d4d40823e8abe.jpg', null, '## 1021 Deepest Root (25 分)\nA graph which is connected and acyclic can be considered a tree. The height of the tree depends on the selected root. Now you are supposed to find the root that results in a highest tree. Such a root is called **the deepest root**.\n\n### Input Specification:\n\nEach input file contains one test case. For each case, the first line contains a positive integer *N* (≤104) which is the number of nodes, and hence the nodes are numbered from 1 to *N*. Then *N*−1 lines follow, each describes an edge by given the two adjacent nodes\' numbers.\n\n### Output Specification:\n\nFor each test case, print each of the deepest roots in a line. If such a root is not unique, print them in increasing order of their numbers. In case that the given graph is not a tree, print `Error: K components` where `K` is the number of connected components in the graph.\n\n### Sample Input 1:\n\n```in\n5\n1 2\n1 3\n1 4\n2 5\n```\n\n### Sample Output 1:\n\n```out\n3\n4\n5\n```\n\n### Sample Input 2:\n\n```in\n5\n1 3\n1 4\n2 5\n3 4\n```\n\n### Sample Output 2:\n\n```out\nError: 2 components\n```\n\n代码来自[原文链接：][1]\n代码不是自己写，但注释太少，对于初学者我来讲，实在是噩梦，但仔细发觉这题都非常有趣，而且第一次接触该关于图有关的ACM题目\n。所以硬着头皮用visual逐语句调试，注释了自我认为比较难理解的语句。\n\n如下：\n\n```c++\n#include <iostream>\n#include <vector>\n#include <set>\n#include <algorithm>\nusing namespace std;\nint n, maxheight = 0;\nvector<vector<int>> v;//定义  邻接图\nbool visit[10010];  //标记是否经过路线\nset<int> s;\nvector<int> temp;//存储树最大深度\nvoid dfs(int node, int height) {\n	if (height > maxheight) {\n		temp.clear();//清空，为后面更新最大深度\n		temp.push_back(node);//容器temp最后向量插入node\n		maxheight = height;//记录当前最大深度\n	}\n	else if (height == maxheight) {//若存在与当前树最大深度相等，添加该根结点\n		temp.push_back(node);\n	}\n	visit[node] = true;//标记已访问\n	for (int i = 0; i < v[node].size(); i++) {//查找node能到达的点\n		if (visit[v[node][i]] == false)//是否已经过的点\n			dfs(v[node][i], height + 1);\n	}\n}\nint main() {\n	scanf_s(\"%d\", &n);\n	v.resize(n + 1);//将元素调至n+1个\n	int a, b, cnt = 0, s1 = 0;//cnt为连通分量数量\n	for (int i = 0; i < n - 1; i++) {\n		scanf_s(\"%d%d\", &a, &b);\n		v[a].push_back(b);\n		v[b].push_back(a);\n	}\n	for (int i = 1; i <= n; i++) {\n		if (visit[i] == false) {\n			dfs(i, 1);//第一个dfs判断有多小连通分量，若没有，记录最深根结点为后面第二个dfs做起点\n			if (i == 1) {\n				if (temp.size() != 0) s1 = temp[0];//记录最高高度的结点为后面第二个dfs做起点\n				for (int j = 0; j < temp.size(); j++)\n					s.insert(temp[j]);//存储最高高度的结点\n			}\n			cnt++;//dfs递归完成后，若还有点没visit，则连通分量不止一个\n		}\n	}\n	if (cnt >= 2) {\n		printf(\"Error: %d components\", cnt);\n	}\n	else {\n		temp.clear();\n		maxheight = 0;\n		fill(visit, visit + 10010, false);\n		dfs(s1, 1);//第一个dfs后保留最高高度拥有的结点们，然后从这些结点中的其中任意一个开始dfs得到最高高度的结点们。\n		for (int i = 0; i < temp.size(); i++)\n			s.insert(temp[i]);//存储最高高度的结点\n		for (auto it = s.begin(); it != s.end(); it++)//初始it，每输出值后指针向后加一，直到容器最后的一个元素\n			printf(\"%d\\n\", *it);\n	}\n	return 0;\n}\n```\n\n\n\n[1]: https://www.liuchuo.net/archives/2348', '2', '2020-04-04 15:03:09', '2019-07-04 15:03:09', '0', '0', '1', '', '1', '1');
INSERT INTO `blog_article` VALUES ('12', '1008 Elevator', 'The highest building in our city has only one elevator. A request list is made up with N positive numbers. The numbers denote at which floors the elevator will stop, in specified order. It costs 6 seconds to move the elevator up one floor, and 4 seconds to move down one floor. The elevator will stay for 5 seconds at each stop.', 'https://ponking-blog.oss-cn-beijing.aliyuncs.com/2020/09/07/81686e1246dc458888d46765a7ed4cad.jpg', null, '1008 Elevator\n-------------\n\n>The highest building in our city has only one elevator. A request list is made up with N positive numbers. The numbers denote at which floors the elevator will stop, in specified order. It costs 6 seconds to move the elevator up one floor, and 4 seconds to move down one floor. The elevator will stay for 5 seconds at each stop.\n\n>For a given request list, you are to compute the total time spent to fulfill the requests on the list. The elevator is on the 0th floor at the beginning and does not have to return to the ground floor when the requests are fulfilled.\n\n>Input Specification:\n>Each input file contains one test case. Each case contains a positive integer N, followed by N positive numbers. All the numbers in the input are less than 100.\n><!--more-->\n>Output Specification:\n>For each test case, print the total time on a single line.\n\n>Sample Input:\n>3 2 3 1\n>Sample Output:\n>41\n\n**题意分析**\n\n>电梯每上升一层需要6秒时间，每下降一层需要4秒时间，每到达一层需要等待5秒。（刚开始在0th，每次不需回底层）。\n\n**代码如下**\n\n```\n#include <iostream>\nusing namespace std;\n\nint main()\n{\n	int n, efloor, sfloor=0,cnt;\n	while (cin >> n) {\n		int Time = 0;\n		for (int i = 0; i < n; i++) {\n			cin >> efloor;\n			cnt = efloor - sfloor;//记录需要经过多小楼层\n			if (cnt < 0)\n				Time += -cnt * 4 + 5;\n			else\n				Time += cnt * 6 + 5;\n			sfloor = efloor;//记录目的楼层\n		}\n		printf(\"%d\\n\", Time);\n	}\n	return 0;\n}\n```', '2', '2020-04-04 23:04:45', '2020-04-04 23:04:45', '1', '0', '1', '', '0', '1');
INSERT INTO `blog_article` VALUES ('13', '1005 Spell It Right (20 分)', 'Given a non-negative integer *N*, your task is to compute the sum of all the digits of *N*, and output every digit of the sum in English.', 'https://ponking-blog.oss-cn-beijing.aliyuncs.com/2020/09/02/17ea2393b8b7407f94fb9969bf576984.jpg', null, 'Given a non-negative integer *N*, your task is to compute the sum of all the digits of *N*, and output every digit of the sum in English.\n### Input Specification:\n\nEach input file contains one test case. Each case occupies one line which contains an *N* (≤10100).\n\n### Output Specification:\n\nFor each test case, output in one line the digits of the sum in English words. There must be one space between two consecutive words, but no extra space at the end of a line.\n\n### Sample Input:\n\n```in\n12345\n```\n\n### Sample Output:\n\n```out\none five\n```\n\n题意:略.\n\n分析:相加的sum,利用stringstream转换string,将string每一位字符转为对应的读音.\n\n### 代码:\n```\n#include <iostream>\n#include<string.h>\n#include<sstream>\n\nusing namespace std;\n\nint main()\n{\n    string num[10]={\"zero\",\"one\",\"two\",\"three\",\"four\",\"five\",\"six\",\"seven\",\"eight\",\"nine\"};\n    string s;\n    stringstream ss;\n    long long int sum=0;\n    getline(cin,s);\n    for(int i=0;i<s.length();i++){\n        sum+=s[i]-\'0\';\n    }\n    ss<<sum;\n    ss>>s;\n    for(int i=0;i<s.length();i++){\n        if(i!=0) cout<<\" \";\n        cout<<num[s[i]-\'0\'];\n    }\n    return 0;\n}\n```', '2', '2020-04-07 14:25:00', '2020-04-07 14:25:00', '0', '0', '1', '', '1', '1');
INSERT INTO `blog_article` VALUES ('21', '1090 危险品装箱 (散列)(25 分)', '集装箱运输货物时，我们必须特别小心，不能把不相容的货物装在一只箱子里。比如氧化剂绝对不能跟易燃液体同箱，否则很容易造成爆炸。\n\n本题给定一张不相容物品的清单，需要你检查每一张集装箱货品清单，判断它们是否能装在同一只箱子里', 'https://ponking-blog.oss-cn-beijing.aliyuncs.com/2020/09/02/886ead8efbc9425a883ffcc90282158e.jpg', null, '\n**1090 危险品装箱 (散列)(25 分)**\n\n集装箱运输货物时，我们必须特别小心，不能把不相容的货物装在一只箱子里。比如氧化剂绝对不能跟易燃液体同箱，否则很容易造成爆炸。\n\n本题给定一张不相容物品的清单，需要你检查每一张集装箱货品清单，判断它们是否能装在同一只箱子里。\n\n### 输入格式：\n\n输入第一行给出两个正整数：*N* (≤104) 是成对的不相容物品的对数；*M* (≤100) 是集装箱货品清单的单数。\n\n随后数据分两大块给出。第一块有 *N* 行，每行给出一对不相容的物品。第二块有 *M* 行，每行给出一箱货物的清单，格式如下：\n\n```\nK G[1] G[2] ... G[K]\n```\n\n其中 `K` (≤1000) 是物品件数，`G[i]` 是物品的编号。简单起见，每件物品用一个 5 位数的编号代表。两个数字之间用空格分隔。\n\n### 输出格式：\n\n对每箱货物清单，判断是否可以安全运输。如果没有不相容物品，则在一行中输出 `Yes`，否则输出 `No`。\n\n### 输入样例：\n\n```in\n6 3\n20001 20002\n20003 20004\n20005 20006\n20003 20001\n20005 20004\n20004 20006\n4 00001 20004 00002 20003\n5 98823 20002 20003 20006 10010\n3 12345 67890 23333\n```\n\n### 输出样例：\n\n```out\nNo\nYes\nYes\n```\n\n题意:如题.\n\n分析:该题重点位散列,利用map<int,vector<int> >存储(记录)不相容物品的对数,然后遍历货物清单中的物品是否有不相容的物品.\n\n**代码如下:**\n\n```c++\n#include <iostream>\n#include<cstdio>\n#include<vector>\n#include<map>\n\nusing namespace std;\n\nint main()\n{\n    map<int,vector<int> >  m;\n    int n,k;\n    cin>>n>>k;\n    for(int i=0;i<n;i++){\n        int a,b;\n        cin>>a>>b;\n        m[a].push_back(b);\n        m[b].push_back(a);\n    }\n\n    while(k--){\n        int cnt,flag=0,a[100000]={0};\n        scanf(\"%d\",&cnt);\n        vector<int> v(cnt);\n        for(int i=0;i<cnt;i++){\n            scanf(\"%d\",&v[i]);\n            a[v[i]]=1;\n        }\n\n        for(int i=0;i<v.size();i++){\n            for(int j=0;j<m[v[i]].size();j++)\n                if(a[m[v[i]][j]]==1)  flag=1;\n        }\n        printf(\"%s\\n\",flag?\"No\":\"Yes\");\n    }\n    return 0;\n}\n\n```', '2', '2020-09-02 01:49:25', '2020-09-02 01:49:25', '0', '0', '0', '', '1', '1');
INSERT INTO `blog_article` VALUES ('31', 'No SecurityManager accessible to the calling code, either bound to the org.apache.shiro.util.ThreadC', 'No SecurityManager accessible to the calling code, either bound to the org.apache.shiro.util.ThreadContext or as a vm static singleton.  This is an invalid application configuration.', 'https://ponking-blog.oss-cn-beijing.aliyuncs.com/2020/09/07/48709154400a43149d2f3435e3c7378e.png', null, '# spring boot 整合shiro 错误\n> 最近博客添加缓存功能,发现Shiro中自定义的过滤器不能添加在SpringBoot容器中,否则会抛出(No SecurityManager accessible to the calling code, either bound to the org.apache.shiro.util.ThreadContext or as a vm static singleton.  This is an invalid application configuration.),只需直接new出对象就行.##', '1', '2020-09-26 16:19:18', '2020-09-26 16:19:18', '0', '1', '1', '', '6', '1');
INSERT INTO `blog_article` VALUES ('32', 'string', 'string', 'string', 'string', 'string', '1', '2020-10-02 23:47:13', '2020-10-02 23:47:13', '0', '0', '0', 'string', '0', '0');

-- ----------------------------
-- Table structure for blog_article_tag
-- ----------------------------
DROP TABLE IF EXISTS `blog_article_tag`;
CREATE TABLE `blog_article_tag` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `article_id` bigint(20) NOT NULL COMMENT '文章ID',
  `tag_id` bigint(20) NOT NULL COMMENT '标签ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=382 DEFAULT CHARSET=utf8 COMMENT='文章与标签的对应关系';

-- ----------------------------
-- Records of blog_article_tag
-- ----------------------------
INSERT INTO `blog_article_tag` VALUES ('228', '13', '2');
INSERT INTO `blog_article_tag` VALUES ('248', '4', '2');
INSERT INTO `blog_article_tag` VALUES ('249', '4', '29');
INSERT INTO `blog_article_tag` VALUES ('250', '5', '2');
INSERT INTO `blog_article_tag` VALUES ('251', '5', '29');
INSERT INTO `blog_article_tag` VALUES ('342', '21', '1');
INSERT INTO `blog_article_tag` VALUES ('343', '21', '6');
INSERT INTO `blog_article_tag` VALUES ('344', '21', '4');
INSERT INTO `blog_article_tag` VALUES ('345', '21', '3');
INSERT INTO `blog_article_tag` VALUES ('346', '21', '2');
INSERT INTO `blog_article_tag` VALUES ('347', '21', '5');
INSERT INTO `blog_article_tag` VALUES ('348', '21', '29');
INSERT INTO `blog_article_tag` VALUES ('354', '11', '2');
INSERT INTO `blog_article_tag` VALUES ('355', '11', '29');
INSERT INTO `blog_article_tag` VALUES ('358', '9', '2');
INSERT INTO `blog_article_tag` VALUES ('359', '9', '29');
INSERT INTO `blog_article_tag` VALUES ('360', '10', '2');
INSERT INTO `blog_article_tag` VALUES ('361', '10', '29');
INSERT INTO `blog_article_tag` VALUES ('363', '12', '2');
INSERT INTO `blog_article_tag` VALUES ('364', '12', '29');
INSERT INTO `blog_article_tag` VALUES ('365', '2', '2');
INSERT INTO `blog_article_tag` VALUES ('366', '2', '3');
INSERT INTO `blog_article_tag` VALUES ('367', '2', '29');
INSERT INTO `blog_article_tag` VALUES ('371', '3', '2');
INSERT INTO `blog_article_tag` VALUES ('372', '3', '29');
INSERT INTO `blog_article_tag` VALUES ('377', '31', '5');
INSERT INTO `blog_article_tag` VALUES ('378', '1', '1');
INSERT INTO `blog_article_tag` VALUES ('379', '1', '2');
INSERT INTO `blog_article_tag` VALUES ('380', '1', '3');
INSERT INTO `blog_article_tag` VALUES ('381', '32', '0');

-- ----------------------------
-- Table structure for blog_category
-- ----------------------------
DROP TABLE IF EXISTS `blog_category`;
CREATE TABLE `blog_category` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL COMMENT '分类名',
  `path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '路径参数（../{path}）由分类名称拼音(或英文)组成',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8 COMMENT='分类表';

-- ----------------------------
-- Records of blog_category
-- ----------------------------
INSERT INTO `blog_category` VALUES ('1', '未分类', 'weifenlei');
INSERT INTO `blog_category` VALUES ('2', '个人提升', 'gerentisheng');
INSERT INTO `blog_category` VALUES ('3', '时间管理', 'shijianguanli');
INSERT INTO `blog_category` VALUES ('4', '博客技巧', 'bokejiqiao');
INSERT INTO `blog_category` VALUES ('5', '生活健康', 'shenghuojiank');
INSERT INTO `blog_category` VALUES ('6', '推荐软件', 'tuijianruanjian');
INSERT INTO `blog_category` VALUES ('7', '学习技巧', 'xuexijiq');
INSERT INTO `blog_category` VALUES ('8', '摄影技术', 'sheyingjishu');
INSERT INTO `blog_category` VALUES ('15', '测试分类...', 'string');

-- ----------------------------
-- Table structure for blog_comment
-- ----------------------------
DROP TABLE IF EXISTS `blog_comment`;
CREATE TABLE `blog_comment` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `article_id` bigint(20) DEFAULT NULL COMMENT '评论的文章',
  `author` varchar(50) DEFAULT NULL COMMENT '评论者',
  `email` varchar(200) DEFAULT NULL COMMENT '评论者的邮箱',
  `email_md5` char(32) DEFAULT NULL COMMENT '邮箱MD5值，用于显示gravatar头像',
  `user_agent` varchar(500) DEFAULT NULL COMMENT '评论者的浏览器',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci COMMENT '评论内容',
  `ip` varchar(128) DEFAULT NULL COMMENT '评论者的ip地址',
  `parent_id` bigint(20) DEFAULT '0' COMMENT '引用的回复，0表示没有引用',
  `is_admin` tinyint(4) DEFAULT '0' COMMENT '是否为博主评论  0：否  1：是',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '评论时间',
  `status` tinyint(4) NOT NULL COMMENT '评论状态 0：待审核 1：已发布 2：已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='评论表';

-- ----------------------------
-- Records of blog_comment
-- ----------------------------
INSERT INTO `blog_comment` VALUES ('1', '1', 'zhouhua', 'iszhouhua@163.com', '2bf0ebee5f19445f2af02908d5c3ab0e', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.67 Safari/537.36', '欢迎！', '127.0.0.1', '0', '0', '2018-01-01 15:46:59', '1');
INSERT INTO `blog_comment` VALUES ('2', '1', 'string', 'string', '2bf0ebee5f19445f2af02908d5c3ab0e', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/85.0.4183.121 Safari/537.36', 'string', '0:0:0:0:0:0:0:1', '1', '0', '2020-10-04 11:39:42', '0');
INSERT INTO `blog_comment` VALUES ('3', '1', 'hahahah', 'hahahaha', null, 'dsada', 'adsdasd', 'adsasdasd', '2', '0', '2020-10-04 15:47:33', '0');

-- ----------------------------
-- Table structure for blog_config
-- ----------------------------
DROP TABLE IF EXISTS `blog_config`;
CREATE TABLE `blog_config` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL DEFAULT '' COMMENT '参数名',
  `value` longtext COMMENT '参数值',
  `type` tinyint(4) DEFAULT NULL COMMENT '参数类型 1：全局变量 2：系统配置',
  `description` varchar(500) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_name` (`name`) USING BTREE COMMENT '参数名唯一'
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8 COMMENT='博客配置表';

-- ----------------------------
-- Records of blog_config
-- ----------------------------
INSERT INTO `blog_config` VALUES ('1', 'BLOG_TITLE', '我的个人博客', '1', '网站标题');
INSERT INTO `blog_config` VALUES ('2', 'BLOG_KEYWORDS', '个人博客', '1', '网站关键词，关键字之间用,分隔');
INSERT INTO `blog_config` VALUES ('3', 'BLOG_DESCRIPTION', '我的个人博客', '1', '网站描述');
INSERT INTO `blog_config` VALUES ('4', 'BLOG_URL', 'http://127.0.0.1:8080/', '1', '网站链接');
INSERT INTO `blog_config` VALUES ('5', 'BLOG_AUTHOR', 'admin', '1', '网站作者');
INSERT INTO `blog_config` VALUES ('6', 'BLOG_AVATAR', '/images/avatar.jpg', '1', '头像');
INSERT INTO `blog_config` VALUES ('7', 'BLOG_NOTICE', '公告', '1', '公告');
INSERT INTO `blog_config` VALUES ('8', 'DEFAULT_IMAGE', '/images/default-preview.jpg', '1', '默认预览图');
INSERT INTO `blog_config` VALUES ('9', 'AUTHOR_DESCRIPTION', '一二三四五，上山打老虎。', '1', '头像下的描述内容');
INSERT INTO `blog_config` VALUES ('10', 'FILING_ICP', null, '1', 'ICP备案');
INSERT INTO `blog_config` VALUES ('11', 'FILING_SECURITY', null, '1', '公安备案');
INSERT INTO `blog_config` VALUES ('12', 'COMMENT_CHECK', 'false', '2', '评论是否需要校检');
INSERT INTO `blog_config` VALUES ('13', 'BACKGROUND_LIST', '[\"/images/slide/background1.jpg\",\"/images/slide/background2.jpg\",\"/images/slide/background3.jpg\",\"/images/slide/background4.jpg\",\"/images/slide/background5.jpg\",\"/images/slide/background6.jpg\"]', '1', '网站的背景图片集合，格式为JSON数组');
INSERT INTO `blog_config` VALUES ('14', 'BLOG_HEAD', '<meta name=\"google-site-verification\" content=\"_-xMXp-rAz3pUIziyjcIoJ8D9VOV6yb7XrB5qaeq_Fg\" />\n<meta name=\"baidu-site-verification\" content=\"HsvFoNGZDC\" />\n<meta name=\"360-site-verification\" content=\"42179cf63604add68d1e11b26f44c322\" />\n<meta name=\"sogou_site_verification\" content=\"XvRe3wQaqS\"/>\n', '1', '博客头部插入的代码，如站点验证代码等');
INSERT INTO `blog_config` VALUES ('15', 'BLOG_SCRIPT', '<script>/*百度统计*/     var _hmt = _hmt || [];    (function() {        var hm = document.createElement(\"script\");        hm.src = \"https://hm.baidu.com/hm.js?77737a53e73e57c6c44b4640d1c108a1\";        var s = document.getElementsByTagName(\"script\")[0];        s.parentNode.insertBefore(hm, s);    })();</script>\n<script>/*百度自动推送*/  (function(){     var bp = document.createElement(\'script\');     var curProtocol = window.location.protocol.split(\':\')[0];     if (curProtocol === \'https\') {         bp.src = \'https://zz.bdstatic.com/linksubmit/push.js\';     }     else {         bp.src = \'http://push.zhanzhang.baidu.com/push.js\';     }     var s = document.getElementsByTagName(\"script\")[0];     s.parentNode.insertBefore(bp, s); })(); </script>\n<script>/*360自动收录*/ (function(){ var src = (document.location.protocol == \"http:\") ? \"http://js.passport.qihucdn.com/11.0.1.js?6396cb521d4daf069727dc8d995a3878\":\"https://jspassport.ssl.qhimg.com/11.0.1.js?6396cb521d4daf069727dc8d995a3878\"; document.write(\'<script src=\"\' + src + \'\" id=\"sozz\"><\\/script>\'); })(); </script>\n', '1', '博客尾部插入的脚本，如统计代码、推送代码等');
INSERT INTO `blog_config` VALUES ('16', 'FILE_STORAGE', '{\"aliyunAccessKeyId\":\"\",\"aliyunAccessKeySecret\":\"\",\"aliyunBucketName\":\"\",\"aliyunDomain\":\"\",\"aliyunEndPoint\":\"\",\"aliyunPrefix\":\"\",\"qcloudBucketName\":\"\",\"qcloudDomain\":\"\",\"qcloudPrefix\":\"\",\"qcloudSecretId\":\"\",\"qcloudSecretKey\":\"\",\"qiniuAccessKey\":\"\",\"qiniuBucketName\":\"\",\"qiniuDomain\":\"\",\"qiniuPrefix\":\"\",\"qiniuSecretKey\":\"\",\"localDirectory\":\"\",\"localDomain\":\"\",\"type\":4,\"localPrefix\":\"upload\"}', '2', '云/本地存储配置信息');

-- ----------------------------
-- Table structure for blog_link
-- ----------------------------
DROP TABLE IF EXISTS `blog_link`;
CREATE TABLE `blog_link` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL COMMENT '链接名称',
  `url` varchar(500) DEFAULT NULL COMMENT '链接地址',
  `type` tinyint(4) DEFAULT NULL COMMENT '链接类型 1：友情链接 2：个人链接',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='链接表';

-- ----------------------------
-- Records of blog_link
-- ----------------------------
INSERT INTO `blog_link` VALUES ('1', '个人博客', 'https://www.ponking.top', '1');
INSERT INTO `blog_link` VALUES ('2', 'GitHub', 'https://ponking.github,io', '2');

-- ----------------------------
-- Table structure for blog_log
-- ----------------------------
DROP TABLE IF EXISTS `blog_log`;
CREATE TABLE `blog_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `ip` varchar(128) NOT NULL COMMENT 'IP地址',
  `city` varchar(20) DEFAULT NULL COMMENT '所在城市',
  `url` varchar(200) DEFAULT NULL COMMENT '首次访问的链接',
  `referer` varchar(500) DEFAULT NULL COMMENT '首次访问的来源',
  `user_agent` varchar(500) DEFAULT NULL COMMENT '首次访问的浏览器类型',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '最后访问时间',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `visits` int(11) DEFAULT '1' COMMENT '总访问次数',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_ip` (`ip`) USING BTREE COMMENT 'IP唯一'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='访客日志表';

-- ----------------------------
-- Records of blog_log
-- ----------------------------

-- ----------------------------
-- Table structure for blog_menu
-- ----------------------------
DROP TABLE IF EXISTS `blog_menu`;
CREATE TABLE `blog_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL COMMENT '菜单名',
  `url` varchar(500) DEFAULT NULL COMMENT '菜单链接',
  `is_blank` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否在新窗口打开菜单  0：否，1：是',
  `icon` varchar(100) DEFAULT NULL COMMENT 'Font Awesome图标',
  `sort` int(11) NOT NULL DEFAULT '0' COMMENT '菜单排序，越小的越靠前',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_url` (`url`) COMMENT '菜单链接唯一索引'
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='菜单表';

-- ----------------------------
-- Records of blog_menu
-- ----------------------------
INSERT INTO `blog_menu` VALUES ('1', '首页', '/', '0', 'fa-home', '1');
INSERT INTO `blog_menu` VALUES ('2', '分类', '/category/', '0', 'fa-list', '3');
INSERT INTO `blog_menu` VALUES ('3', '标签', '/tag/', '0', 'fa-tags', '4');
INSERT INTO `blog_menu` VALUES ('4', '关于我', '/about.html', '0', 'fa-user', '10');

-- ----------------------------
-- Table structure for blog_spider
-- ----------------------------
DROP TABLE IF EXISTS `blog_spider`;
CREATE TABLE `blog_spider` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL COMMENT '爬虫标识名',
  `title_rule` varchar(500) NOT NULL COMMENT '标题爬取规则',
  `content_rule` varchar(500) NOT NULL COMMENT '文章内容爬取规则',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='爬虫规则';

-- ----------------------------
-- Records of blog_spider
-- ----------------------------
INSERT INTO `blog_spider` VALUES ('1', '博客园', 'h1.postTitle', 'div.postBody');
INSERT INTO `blog_spider` VALUES ('2', 'CSDN', 'h1.title-article', 'div#content_views');
INSERT INTO `blog_spider` VALUES ('3', '简书', 'h1.title', 'div.show-content');
INSERT INTO `blog_spider` VALUES ('4', '思否', '#articleTitle a', 'div.article');

-- ----------------------------
-- Table structure for blog_tag
-- ----------------------------
DROP TABLE IF EXISTS `blog_tag`;
CREATE TABLE `blog_tag` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL COMMENT '标签名',
  `path` varchar(255) DEFAULT NULL COMMENT '路径参数（../{path}）由标签名称拼音组成',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8 COMMENT='标签表';

-- ----------------------------
-- Records of blog_tag
-- ----------------------------
INSERT INTO `blog_tag` VALUES ('1', 'Spring Boot', null);
INSERT INTO `blog_tag` VALUES ('2', 'ACM', null);
INSERT INTO `blog_tag` VALUES ('3', 'C#', null);
INSERT INTO `blog_tag` VALUES ('4', 'LeetCode', null);
INSERT INTO `blog_tag` VALUES ('5', 'Java', null);
INSERT INTO `blog_tag` VALUES ('6', '动态规划', null);
INSERT INTO `blog_tag` VALUES ('29', 'C++', null);
INSERT INTO `blog_tag` VALUES ('32', '哈哈哈哈', null);

-- ----------------------------
-- Table structure for sys_task
-- ----------------------------
DROP TABLE IF EXISTS `sys_task`;
CREATE TABLE `sys_task` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `job_name` varchar(255) DEFAULT NULL COMMENT '任务名',
  `description` varchar(255) DEFAULT NULL COMMENT '任务描述',
  `cron_expression` varchar(255) DEFAULT NULL COMMENT 'cron表达式',
  `bean_class` varchar(255) DEFAULT NULL COMMENT '任务执行时调用哪个类的方法 包名+类名',
  `job_status` varchar(255) DEFAULT NULL COMMENT '任务状态',
  `job_group` varchar(255) DEFAULT NULL COMMENT '任务分组',
  `create_user` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` varchar(64) DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=32 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_task
-- ----------------------------

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `nickname` varchar(50) DEFAULT NULL COMMENT '用户昵称',
  `password` char(32) NOT NULL,
  `salt` char(16) NOT NULL COMMENT '密码盐',
  `email` varchar(200) DEFAULT NULL COMMENT '邮箱',
  `email_md5` char(32) DEFAULT NULL COMMENT '邮箱MD5值，用于显示gravatar头像',
  `last_login` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后登录时间',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `login_fail` tinyint(4) NOT NULL DEFAULT '0' COMMENT '登录失败次数，超过一定次数禁止登录',
  `is_disable` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否禁用 0：否 1：是',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', 'admin', '管理员', 'ea49ba308fa797beeaa90e482c0576b6', '7af4a47cb431d8f4', 'admin@admin.com', '64e1b8d34f425d19e1ee2ea7236d3028', '2018-12-31 20:13:25', '2018-12-31 20:13:25', '0', '0');
