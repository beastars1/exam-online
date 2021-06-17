/*
 Navicat Premium Data Transfer

 Source Server         : localMysql
 Source Server Type    : MySQL
 Source Server Version : 80021
 Source Host           : localhost:3306
 Source Schema         : exam_online

 Target Server Type    : MySQL
 Target Server Version : 80021
 File Encoding         : 65001

 Date: 17/06/2021 11:36:44
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin`  (
  `admin_id` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `admin_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `pwd` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `role` int NULL DEFAULT NULL,
  PRIMARY KEY (`admin_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 20210404 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for choice_question
-- ----------------------------
DROP TABLE IF EXISTS `choice_question`;
CREATE TABLE `choice_question`  (
  `choice_id` bigint NOT NULL AUTO_INCREMENT,
  `analysis` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `choice_answer` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `course` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `option_a` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `option_b` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `option_c` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `option_d` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `choice_content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `choice_score` int NULL DEFAULT NULL,
  PRIMARY KEY (`choice_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 55 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of choice_question
-- ----------------------------
INSERT INTO `choice_question` VALUES (1, NULL, 'C', '计算机网络', '将IP地址翻译为计算机名，为客户机分配IP地址', '将IP地址翻译为计算机名、解析计算机的MAC地址', '将计算机名翻译为IP地址、为客户机分配IP地址', '将计算机名翻译为IP地址、解析计算机的MAC地址', 'DNS 服务器和DHCP服务器的作用是（）。', 4);
INSERT INTO `choice_question` VALUES (2, NULL, 'A', '计算机网络', 'ipconfig/displaydns', 'nslookup', 'ipconfig/release', 'ipconfig/flushdns', '查看DNS缓存记录的命令（）。', 4);
INSERT INTO `choice_question` VALUES (3, NULL, 'D', '计算机网络', 'ARP', 'DHCP', 'UDP', 'TCP', 'HTTP协议通常使用什么协议进行传输（）。', 4);
INSERT INTO `choice_question` VALUES (4, NULL, 'A', '计算机网络', 'DhcpDisover', 'DhcpOffer', 'DhcpAck', 'DhcpNack', 'DHCP（）报文的目的IP地址为255.255.255.255。', 4);
INSERT INTO `choice_question` VALUES (5, NULL, 'B', '计算机网络', '196.254.109.100', '169.254.12.42', '69.254.48.45', '96.254.54.15', '下列地址中，（  ）不是DHCP服务器分配的IP地址。', 4);
INSERT INTO `choice_question` VALUES (6, NULL, 'A', '计算机网络', 'IP，掩码，网关，DNS', 'IP，掩码，域名，SMTP', '网关，掩码，浏览器，FTP', 'IP，网关，DNS，服务器', 'DHCP通常可以为客户端自动配置哪些网络参数（）。', 4);
INSERT INTO `choice_question` VALUES (7, NULL, 'A', '计算机网络', '本地缓存记录→区域记录→转发域名服务器→根域名服务器', '区域记录→本地缓存记录→转发域名服务器→根域名服务器', '本地缓存记录→区域记录→根域名服务器→转发域名服务器', '区域记录→本地缓存记录→根域名服务器→转发域名服务器', 'DNS服务器在名称解析过程中正确的查询顺序为（）。', 4);
INSERT INTO `choice_question` VALUES (8, NULL, 'A', '计算机网络', '1024', '64', '256', '128', '在TCP/IP协议中，序号小于（）的端口称为熟知端口(well-known port)。', 4);
INSERT INTO `choice_question` VALUES (9, NULL, 'A', '计算机网络', 'UDP协议的低开销特性', 'UDP协议的高开销特性', 'TCP协议的低开销特性', 'TCP协议的高开销特性', '在Internet上用TCP/IP播放视频，想用传输层的最快协议，以减少时延，要使用（ ）。', 4);
INSERT INTO `choice_question` VALUES (10, NULL, 'A', '计算机网络', '端口号', 'IP地址', '协议类型', 'MAC地址', '在TCP协议中采用（ ）来区分不同的应用进程。', 4);
INSERT INTO `choice_question` VALUES (11, NULL, 'D', '计算机网络', '使用面向连接的会话', '使用“尽力而为”的传输', '使用滑动窗口来维持可靠性', '使用确认重传机制来确保传输的数据不丢失', '可靠的传输协议中的“可靠”指的是（ ）。', 4);
INSERT INTO `choice_question` VALUES (12, NULL, 'A', '计算机网络', '50KB', '80KB', '130KB', '30KB', '假设拥塞窗口为50KB，接收窗口为80KB，TCP能够发送的最大字节数为（ ）。', 4);
INSERT INTO `choice_question` VALUES (13, NULL, 'C', '计算机网络', '（SYN=0,ACK=0,seq=2001,ack=2001）', '（SYN=1,ACK=1,seq=2000,ack=2000）', '（SYN=1,ACK=1,seq=2001,ack=2001）', '（SYN=0,ACK=1,seq=2000,ack=2000）', '主机A向主机B发送一个（SYN=1，seq=2000）的TCP报文，期望与主机B建立连接，若主机B接受连接请求，则主机B发送的正确有TCP报文可能是（ ）。', 4);
INSERT INTO `choice_question` VALUES (14, NULL, 'B', '计算机网络', '70', '30', '100', '170', '主机A向主机B连续发送了两个TCP报文段，其序号分别为70和100。试问：第一个报文段携带了（）个字节的数据？', 4);
INSERT INTO `choice_question` VALUES (15, NULL, 'A', '计算机网络', '采样、量化、编码', '量化、编码、采样', '编码、量化、采样', '采样、编码、量化', 'PCM脉码调制的过程（ ）。', 4);
INSERT INTO `choice_question` VALUES (16, NULL, 'B', '计算机网络', '600Baud', '1200Baud', '4800Baud', '9600Baud', '若某采用4相位调制的通信链路的数据传输速率为2400bps，则该链路的波特率为（）。', 4);
INSERT INTO `choice_question` VALUES (17, NULL, 'C', '计算机网络', '数据传输速率表示每秒钟传输构成数据代码的二进制比特数', '对于二进制数据，数据传输速率为S=1/T (bps)', '常用的数据传输速率单位有: 1Mbps=1.024×106bps', '数据传输速率是描述数据传输系统性能的重要技术指标之一', '以下关于数据传输速率的描述中，错误的是（）。', 4);
INSERT INTO `choice_question` VALUES (18, NULL, 'D', '计算机网络', '时分多路复用将线路使用的时间分成多个时间片', '时分多路复用分为同步时分多路复用与统计时分多路复用', '时分多路复用使用“帧”与数据链路层“帧”的概念、作用是不同的', '统计时分多路复用将时间片预先分配给各个信道', '以下关于时分多路复用概念的描述中，错误的是（）。', 4);
INSERT INTO `choice_question` VALUES (19, NULL, 'A', '计算机网络', '双绞线', '同轴电缆', '光纤', '无线电', '1000BASE-T标准支持的传输介质是（）。', 4);
INSERT INTO `choice_question` VALUES (20, NULL, 'D', '计算机网络', '直通交换', '无碎片交换', '无差错交换', '存储转发交换', '一个以太网交换机，读取整个数据帧，对数据帧进行差错校验后再转发出去，这种交换方式称为（）。', 4);
INSERT INTO `choice_question` VALUES (21, NULL, 'D', '计算机网络', '一个新的交换机没有配置VLAN', '通过配置VLAN减少了冲突域的数量', '一个VLAN不能跨越多个交换机', '各个VLAN属于不同的广播域', '关于VLAN，下面的描述中正确的是（）。', 4);
INSERT INTO `choice_question` VALUES (22, NULL, 'B', '计算机网络', '用于不同子网中的主机进行通信', '作为第二层设备的唯一标识', '用于区别第二层第三层的协议数据单元', '保存主机可检测未知的远程设备', '以太网协议中使用物理地址作用是什么？', 4);
INSERT INTO `choice_question` VALUES (23, NULL, 'A', '计算机网络', '冲突次数越多，后退的时间越短', '平均后退次数的多少与负载大小有关', '后退时延的平均值与负载大小有关', '重发次数达到一定极限后放弃发送', '以太网采用的CSMA/CD协议，当冲突发生时要通过二进制指数后退算法计算后退延时， 关于这个算法，以下论述中错误的是（）。', 4);
INSERT INTO `choice_question` VALUES (24, NULL, 'B', '计算机网络', '交换机从路由表中提取设备的MAC地址', '交换机检查端口流入分组的源地址', '交换机之间互相交换地址表', '网络管理员手工输入设备的MAC地址', '以下关于交换机获取与其端口连接设备的MAC地址的叙述中，正确的是（）。', 4);
INSERT INTO `choice_question` VALUES (25, NULL, 'B', '计算机网络', '1101011001', '101011011', '11011011', '1011001', '如果G(x)为11010010，以下4个CRC校验比特序列中只有哪个可能是正确的？', 4);
INSERT INTO `choice_question` VALUES (26, NULL, 'C', '计算机网络', 'Ethernet物理地址又叫做MAC地址', '48位的Ethernet物理地址允许分配的地址数达到247个', '网卡的物理地址写入主机的EPROM中', '48位的Ethernet物理地址允许分配的地址数达到247个', '以下关于Ethernet物理地址的描述中，错误的是？', 4);
INSERT INTO `choice_question` VALUES (27, NULL, 'B', '计算机网络', '信息帧', '确认帧', '监控帧', '无编号帧', '下列帧类型中，不属于HDLC帧类型的是（）。', 4);
INSERT INTO `choice_question` VALUES (28, NULL, 'B', '计算机网络', '组成一个冲突域，但不是一个广播域', '组成一个广播域，但不是一个冲突域', '既不一个冲突域，也不是一个广播域', '组成一个冲突域，也是一个广播域', '通过交换机连接的一组站点，关于它们的广播域和冲突域说法正确的是（）。', 4);
INSERT INTO `choice_question` VALUES (29, NULL, 'A', '计算机网络', '帧', '字节', '比特', '分组', '数据链路层的数据单位是（）。', 4);
INSERT INTO `choice_question` VALUES (30, NULL, 'A', '计算机网络', 'MAC，LLC等三层', 'LLC，MHS等三层', 'LLC，VT等三层', 'MAC，FTAM等三层', 'LAN参考模型可分为物理层、（ ）。', 4);
INSERT INTO `choice_question` VALUES (31, '', 'C', '计算机网络', '8', '16', '24', '32', '在地址219.25.23.56中的默认的网络掩码有多少位?', 4);
INSERT INTO `choice_question` VALUES (32, NULL, 'B', '党史', '改革开放', '人民民主专政', '以经济建设为中心', '以廉政建设为中心', '我国的立国之本是：坚持社会主义道路；坚持（）；坚持中国共产党的领导；坚持马克思列宁主义毛泽东思想。', 4);
INSERT INTO `choice_question` VALUES (33, NULL, 'C', '党史', '北伐战争的开始', '黄埔军校的建立', '国民党一大', '中国共产党第三次全国代表大会', '第一次国共合作正式建立的标志是：()。', 4);
INSERT INTO `choice_question` VALUES (34, NULL, 'A', '党史', '刘胡兰', '赵一曼', '李秋岳', '李林', '“生的伟大，死的光荣”指的是()。', 4);
INSERT INTO `choice_question` VALUES (35, NULL, 'D', '党史', '经济基础', '生产关系', '自然资源', '物质生产力', '马克思主义认为，()是全部社会生活的物质前提。', 4);
INSERT INTO `choice_question` VALUES (36, NULL, 'A', '党史', '中国特色社会主义道路', '四项基本原则', '以经济建议为中心', '改革开放', '党的十七大报告强调，高举中国特色社会主义伟大旗帜，最根本的就是要坚持()和中国特色社会主义理论体系。', 4);
INSERT INTO `choice_question` VALUES (37, NULL, 'A', '党史', '《中华人民共和国反分裂国家法》', '《中华人民共和国国家安全法》', '《中华人民共和国反恐怖主义法》', '《中华人民共和国反间谍法》', '2005年3月14日，十届全国人大三次会议高票通过()，充分体现了党和国家以最大诚意和尽最大努力争取和平统一的一贯立场，表明了维护国家统一与领土完整的坚定决心。', 4);
INSERT INTO `choice_question` VALUES (38, NULL, 'C', '党史', '党的一切权力集中于前委指导机关', '实行土地革命，建立苏维埃政权', '用无产阶级思想进行军队和党的建设', '农村包围城市，武装夺取政权', '古田会议决议的中心思想是()。', 4);
INSERT INTO `choice_question` VALUES (39, NULL, 'D', '党史', '十五届四中全会', '十四大', '十三届四中全会', '十五大', '依法治国是党领导人民治理国家的基本方略，这一方略被写进宪法是在党的()。', 4);
INSERT INTO `choice_question` VALUES (40, NULL, 'C', '党史', '邱少云', '张思德', '董存瑞', '朱瑞', '在解放隆化县时，为了炸弹敌人的暗堡，用自己的身体托起炸药包，英勇牺牲，年仅19岁的是()。', 4);
INSERT INTO `choice_question` VALUES (41, NULL, 'B', '党史', '1951年《关于知识分子的改造问题》的报告中', '1956年中共中央专门如开的知识分子问题会议上的报告中', '1962年广州的科技工作会议和文艺工作会议上的讲话中', '1956年党的八大报告中', '周恩来代表中央发出“向现代科学进军”的动员令是在()。', 4);
INSERT INTO `choice_question` VALUES (42, NULL, 'D', '党史', '监督检查', '改革他新', '自我革命', '党的建设', '党的十九届四中全会提出，要坚持人民军队最高领导权和指挥权属于党中央，健全人民军队()制度体系，把党对人民军队的绝对领导贯彻到军队建设各领域全过程。', 4);
INSERT INTO `choice_question` VALUES (43, NULL, 'D', '党史', '国家', '政府', '消费者', '市场', '在资源配置起决定性作用的是()。', 4);
INSERT INTO `choice_question` VALUES (44, NULL, 'C', '党史', '③④', '①③④', '①②③', '①③', '19991年12月20日零时，中华人民共和国政府恢复对澳门行使主权，从此澳门回到祖国的怀抱。下面发生在同年的大事有()。①欧元正式问世②中国加入亚太经合组织 ③“神舟一号”试验飞船成功发射与回收 ④“依法治国、建设社会主义法治国家”被写入宪法', 4);
INSERT INTO `choice_question` VALUES (45, NULL, 'C', '党史', '建立以工农联盟为基础的广泛的统一战线', '建立一支无产阶级领导的以农民为主体的强大的革命武装', '加强无产阶级政党的建设', '在同资产阶级建立统一战线时，坚持独立自立的原则，同时实行又联合又斗争的方针', '无产阶级及其政党实现领导权的根本保证是()。', 4);
INSERT INTO `choice_question` VALUES (46, NULL, 'A', '党史', '高级干部', '基层干部', '中级干部', '普通党员', '邓小平在1979年针对党内出现的脱离群众问题，指出：“为了整顿党风，搞好民风，要先从我们()整起。”', 4);
INSERT INTO `choice_question` VALUES (47, NULL, 'B', '党史', '《塘沽协定》', '《淞沪停战协定》', '《何梅协定》', '《上海条约》', '九一八事变爆发后，民族危机急剧加深，但国民党政府的基本方针仍是求和。经英、美等国协调，国民党政府同日本侵略者于1932年5月签订了丧权辱国的()。', 4);
INSERT INTO `choice_question` VALUES (48, NULL, 'D', '党史', '钱三强', '屠呦呦', '茅以升', '袁隆平', '1964年，湖南安江农业学校教师()开创了杂交水稻研究。', 4);
INSERT INTO `choice_question` VALUES (49, NULL, 'A', '党史', '改革开放', '创新', '科学', '中国共产党', '只有社会主义才能救中国，只有()才能发展中国、发展社会主义、发展马克思主义。', 4);
INSERT INTO `choice_question` VALUES (50, NULL, 'B', '党史', '快速发展', '与时俱进', '改革开放', '全面小康', '党的十七大报告提出，新时期最突出的标志是()。', 4);
INSERT INTO `choice_question` VALUES (51, NULL, 'A', '党史', '李大钊发表《我的马克思主义观》', '李大钊发表《庶民的胜利》和《布尔什维主义的胜利》', '《新青年》《每周评论》《民国日报》《建设》等报刊纷纷发表宣传马克思的文章', '李达翻译《唯物史观解说》《马克思经济学说》《社会问题总览》', '()，标志着马克思主义在中国进入比较系统的传播阶段。', 4);
INSERT INTO `choice_question` VALUES (52, NULL, 'C', '党史', '7', '8', '9', '10', '从1963年9月至1964年7月，中共中央相继发表()篇评论苏共中央公开信的文章，点名批评“赫鲁晓夫修正主义”。', 4);
INSERT INTO `choice_question` VALUES (53, NULL, 'C', '党史', '刘少奇', '毛泽东', '邓小平', '江泽民', '明确提出“发展才是硬道理”的是()。', 4);

-- ----------------------------
-- Table structure for exam
-- ----------------------------
DROP TABLE IF EXISTS `exam`;
CREATE TABLE `exam`  (
  `exam_id` bigint NOT NULL AUTO_INCREMENT,
  `exam_course` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `department` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `exam_desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `exam_date` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `full_score` int NULL DEFAULT NULL,
  `grade` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `major` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `exam_notes` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `paper_id` bigint NULL DEFAULT NULL,
  `term` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `exam_total_time` int NULL DEFAULT NULL,
  PRIMARY KEY (`exam_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for fill_question
-- ----------------------------
DROP TABLE IF EXISTS `fill_question`;
CREATE TABLE `fill_question`  (
  `fill_id` bigint NOT NULL AUTO_INCREMENT,
  `analysis` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `fill_answer` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `course` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `fill_content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `fill_score` int NULL DEFAULT NULL,
  PRIMARY KEY (`fill_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 64 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of fill_question
-- ----------------------------
INSERT INTO `fill_question` VALUES (1, NULL, '通信子网；资源子网', '计算机网络', '从计算机网络系统组成的角度看，计算机网络可以分为()和()。', 5);
INSERT INTO `fill_question` VALUES (2, NULL, '应用', '计算机网络', '收发电子邮件，属于ISO/OSI RM中 ()层的功能。', 5);
INSERT INTO `fill_question` VALUES (3, NULL, 'TCP；UDP', '计算机网络', '在TCP/IP层次模型中与OSI参考模型第四层相对应的主要协议有()和(),其中后者提供无连接的不可靠传输服务。', 5);
INSERT INTO `fill_question` VALUES (4, NULL, '同轴电缆；双绞线；光纤', '计算机网络', '计算机网络中常用的三种有线媒体是 (),()和 ()。', 5);
INSERT INTO `fill_question` VALUES (5, NULL, 'CERnet（中国教育科研网）', '计算机网络', '国内最早的四大网络包括原邮电部的ChinaNet. 原电子部的ChinaGBN. 教育部的()和中科院的CSTnet。', 5);
INSERT INTO `fill_question` VALUES (6, NULL, '广域网；局域网；城域网', '计算机网络', '复盖一个国家，地区或几个洲的计算机网络称为()，在同一建筑或复盖几公里内范围的网络称为()，而介于两者之间的是()。', 5);
INSERT INTO `fill_question` VALUES (7, NULL, 'POP3；SMTP', '计算机网络', 'Outlook等常用电子邮件软件接收邮件使用的协议是(),发送邮件时使用的协议是()。', 5);
INSERT INTO `fill_question` VALUES (8, NULL, '基带', '计算机网络', '通信系统中，称调制前的电信号为()信号，调制后的信号为调制信号。', 5);
INSERT INTO `fill_question` VALUES (9, NULL, 'C', '计算机网络', '按照IPV4标准,IP地址205.3.127.13属于()类地址。', 5);
INSERT INTO `fill_question` VALUES (10, NULL, '分组交换电路交换', '计算机网络', '计算机网络采用()技术，而传统电话网络则采用()技术。', 5);
INSERT INTO `fill_question` VALUES (11, NULL, '数字信号模拟信号', '计算机网络', '计算机内传输的信号是()，而公用电话系统的传输系统只能传输()。', 5);
INSERT INTO `fill_question` VALUES (12, NULL, '基带信号调制信号', '计算机网络', '通信系统中，称调制前的电信号为()，调制后的信号叫()。', 5);
INSERT INTO `fill_question` VALUES (13, NULL, '网络报头；数据区', '计算机网络', 'IP地址协议作网间网中()层协议，提供无连接的数据报传输机制，IP数据报也分为()和()两个部分。', 5);
INSERT INTO `fill_question` VALUES (14, NULL, 'TELNET', '计算机网络', '()是一个简单的远程终端协议。', 5);
INSERT INTO `fill_question` VALUES (15, NULL, '接口；服务', '计算机网络', '在同一个系统内，相邻层之间交换信息的连接点称之为()，而低层模块向高层提供功能性的支持称之为()。', 5);
INSERT INTO `fill_question` VALUES (16, NULL, 'SMTP', '计算机网络', 'Internet广泛使用的电子邮件传送协议是()。', 5);
INSERT INTO `fill_question` VALUES (17, NULL, '分组交换网；电路交换网；报文交换网', '计算机网络', '按交换方式来分类，计算机网络可以分为()，()和()三种', 5);
INSERT INTO `fill_question` VALUES (18, NULL, '网络；应用；服务', '计算机网络', 'Intranet分层结构包括()、()，()三个层次。', 5);
INSERT INTO `fill_question` VALUES (19, NULL, '统一资源定位器/URL/url', '计算机网络', 'WWW上的每一个网页都有一个独立的地址，这些地址称为()。', 5);
INSERT INTO `fill_question` VALUES (20, NULL, '路由选择；流量控制；差错纠正', '计算机网络', '分组交换网中，附加信息用来在网络中进行()、() 和 ()。', 5);
INSERT INTO `fill_question` VALUES (21, NULL, 'MAC', '计算机网络', '根据IEEE802模型的标准将数据链路层划分为LLC子层和 ()子层。', 5);
INSERT INTO `fill_question` VALUES (22, NULL, '链路状态算法；距离向量算法', '计算机网络', '据交换的路由信息的不同，路由算法可以分为两大类： ()  和 () 。', 5);
INSERT INTO `fill_question` VALUES (23, NULL, '80000', '计算机网络', '假定某信道受奈氏准则限制的最高码元速率为2000码元/秒。如果采用振幅调制，把码元的振幅划分为16个不同等级来传送，那么可以获得的数据率为 () b/s。', 5);
INSERT INTO `fill_question` VALUES (25, NULL, '以太网交换器', '计算机网络', '交换型以太网系统中的 ()  ，以其为核心联接站点或者网段，端口之间帧的输入和输出已不再受到CSMA/CD媒体访问控制协议的约束。', 5);
INSERT INTO `fill_question` VALUES (26, NULL, 'IEEE802', '计算机网络', '局域网络参考模型是以 ()标准为基础的', 5);
INSERT INTO `fill_question` VALUES (27, NULL, '路由表', '计算机网络', '路由器的核心是 () 。', 5);
INSERT INTO `fill_question` VALUES (28, NULL, '10111110110', '计算机网络', '若 HDLC 帧数据段中出现比特串“ 01011111110 ”，则比特填充后的输出为()。', 5);
INSERT INTO `fill_question` VALUES (29, NULL, '移幅键控法ASK；移相键控法PSK；移频键控法FSK', '计算机网络', '数字调制的三种基本形式：()、 ()、()。', 5);
INSERT INTO `fill_question` VALUES (30, '', '非连接(无连接)', '计算机网络', 'UDP可以为用户提供不可靠的、面向()的传输服务。', 5);
INSERT INTO `fill_question` VALUES (33, '', '片偏移', '计算机网络', '标识、标志和()主要用于数据包的分片。', 5);
INSERT INTO `fill_question` VALUES (34, NULL, '五四运动', NULL, '标志着中国新民主主义革命开端的历史事件是()。', NULL);
INSERT INTO `fill_question` VALUES (35, NULL, '五四运动', '党史', '标志着中国新民主主义革命开端的历史事件是()。', 5);
INSERT INTO `fill_question` VALUES (36, NULL, '中共二大', '党史', '第一次明确提出彻底的反帝反封建民主革命纲领的会议是()。', 5);
INSERT INTO `fill_question` VALUES (37, NULL, '国民党第一次全国代表大会的召开', '党史', '第一次国共合作正式建立的标志是()。', 5);
INSERT INTO `fill_question` VALUES (38, NULL, '南昌起义', '党史', '标志着中国共产党开始独立领导革命战争和创建人民军队的事件是()。', 5);
INSERT INTO `fill_question` VALUES (39, NULL, '八七会议', '党史', '“政权是由枪杆子中取得的”这一论断是毛泽东在()提出的。', 5);
INSERT INTO `fill_question` VALUES (40, NULL, '三湾改编', '党史', '()从组织上确立了党对军队的绝对领导，为建立一支无产阶级领导下的新型人民军队奠定了基础。', 5);
INSERT INTO `fill_question` VALUES (41, NULL, '遵义会议', '党史', '红军长征途中召开的()，开始确立了毛泽东在党中央和红军的领导地位，挽救了党、挽救了红军、挽救了中国革命，成为党的历史上一个生死攸关的转折点。', 5);
INSERT INTO `fill_question` VALUES (42, NULL, '瓦窑堡会议', '党史', '1935年12月，中共中央在()上确定了抗日民族统一战线的策略方针。', 5);
INSERT INTO `fill_question` VALUES (43, NULL, '卢沟桥', '党史', '1937年()事变爆发，全面抗战由此开始。', 5);
INSERT INTO `fill_question` VALUES (44, NULL, '平型关大捷', '党史', '全面抗战爆发后，八路军打的第一个大胜仗是()。', 5);
INSERT INTO `fill_question` VALUES (45, NULL, '百团大战', '党史', '1940年8月至12月，八路军在华北发动的()，给日军以沉重打击。', 5);
INSERT INTO `fill_question` VALUES (46, NULL, '左权', '党史', '1942年5月，八路军副参谋长()在指挥八路军总部进行反“扫荡”战斗时，不幸中弹，壮烈殉国。', 5);
INSERT INTO `fill_question` VALUES (47, NULL, '皖南事变', '党史', '“千古奇冤，江南一叶;同室操戈，相煎何急?”是周恩来在()后对国民党蒋介石的控诉和回击。', 5);
INSERT INTO `fill_question` VALUES (48, NULL, '七大', '党史', '1945年，党的()确立了毛泽东思想为全党的指导思想。', 5);
INSERT INTO `fill_question` VALUES (49, NULL, '重庆谈判', '党史', '抗日战争胜利后，国共双方举行了最高级别谈判，双方代表签署了《政府与中共代表会谈纪要》，这就是历史上著名的()。', 5);
INSERT INTO `fill_question` VALUES (50, NULL, '中原', '党史', '国民党发动全面内战是从1946年进攻()解放区开始的。', 5);
INSERT INTO `fill_question` VALUES (51, NULL, '帝国主义和一切反动派都是纸老虎', '党史', '1946年8月，毛泽东在同美国记者安娜·路易斯·斯特朗的谈话中提出了()。', 5);
INSERT INTO `fill_question` VALUES (52, NULL, '刘少奇', '党史', '1947年7月至9月，在()主持下，中共中央工作委员会在河北平山县西柏坡召开全国土地会议，制定了《中国土地法大纲》。', 5);
INSERT INTO `fill_question` VALUES (53, NULL, '西柏坡', '党史', '1949年3月，中共中央在()召开七届二中全会。毛泽东在会上向全党同志提出了著名的“两个务必”。', 5);
INSERT INTO `fill_question` VALUES (54, NULL, '彭德怀', '党史', '1950年6月朝鲜战争爆发，10月19日以()为司令员兼政治委员的中国人民志愿军奉命开赴朝鲜战场。', 5);
INSERT INTO `fill_question` VALUES (55, NULL, '1956', '党史', '()年底，社会主义改造取得决定性的胜利，基本消灭了剥削制度，全民所有制和劳动群众集体所有制这两种社会主义公有制形式，已在国民经济中占据绝对优势地位。', 5);
INSERT INTO `fill_question` VALUES (56, NULL, '1964', '党史', '()年，我国成功地爆炸第一颗原子弹，有力地打破了超级大国的核垄断和核讹诈。', 5);
INSERT INTO `fill_question` VALUES (57, NULL, '26', '党史', '1971年10月25日，第()届联合国大会通过决议，恢复中华人民共和国在联合国的一切合法权益。', 5);
INSERT INTO `fill_question` VALUES (58, NULL, '十四', NULL, '中国共产党第()次全国代表大会系统论述了社会主义初级阶段理论。', NULL);
INSERT INTO `fill_question` VALUES (59, NULL, '十四', '党史', '中国共产党第()次全国代表大会系统论述了社会主义初级阶段理论。', 5);
INSERT INTO `fill_question` VALUES (60, NULL, '十四', '党史', '中国共产党第()次全国代表大会明确提出了经济体制改革的目标是建立社会主义市场经济体制。', 5);
INSERT INTO `fill_question` VALUES (61, NULL, '一国两制', '党史', '1997年7月1日，香港回归祖国，标志着邓小平()构想获得巨大成功。', 5);
INSERT INTO `fill_question` VALUES (62, NULL, '江泽民', '党史', '把发展问题同党的性质、党的执政理念联系起来，明确提出发展是我们党执政兴国的第一要务的是()。', 5);
INSERT INTO `fill_question` VALUES (63, NULL, '党内民主', '党史', '党的十六大报告指出：“()是党的生命。”', 5);

-- ----------------------------
-- Table structure for judge_question
-- ----------------------------
DROP TABLE IF EXISTS `judge_question`;
CREATE TABLE `judge_question`  (
  `judge_id` bigint NOT NULL AUTO_INCREMENT,
  `analysis` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `judge_answer` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `course` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `judge_content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `judge_score` int NULL DEFAULT NULL,
  PRIMARY KEY (`judge_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 44 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of judge_question
-- ----------------------------
INSERT INTO `judge_question` VALUES (1, NULL, 'T', '计算机网络', '与有线网相比,无线网的数据传输率一般相对较慢。', 2);
INSERT INTO `judge_question` VALUES (2, NULL, 'F', '计算机网络', 'OSI参考模型中,不同节点的同等层具有不同的功能。', 2);
INSERT INTO `judge_question` VALUES (3, NULL, 'F', '计算机网络', '普通电脑不能作为服务器。', 2);
INSERT INTO `judge_question` VALUES (4, NULL, 'F', '计算机网络', '没有网线的电脑不能连入互联网。', 2);
INSERT INTO `judge_question` VALUES (5, NULL, 'T', '计算机网络', '网卡必须安装驱动程序。', 2);
INSERT INTO `judge_question` VALUES (6, NULL, 'F', '计算机网络', 'UTP为屏蔽双绞线。', 2);
INSERT INTO `judge_question` VALUES (7, NULL, 'T', '计算机网络', '网络接口卡又称为网卡,它是构成网络的基本部件。', 2);
INSERT INTO `judge_question` VALUES (8, NULL, 'T', '计算机网络', '无线AP可以成倍地扩展网络覆盖范围。', 2);
INSERT INTO `judge_question` VALUES (9, NULL, 'T', '计算机网络', 'SMTP是一组用于由源地址到目的地址传送邮件的协议。', 2);
INSERT INTO `judge_question` VALUES (11, NULL, 'F', '计算机网络', '任务管理器可以关闭所有的进程。', 2);
INSERT INTO `judge_question` VALUES (12, NULL, 'T', '计算机网络', '利用BT下载时,用户越多,下载速度越快。', 2);
INSERT INTO `judge_question` VALUES (13, NULL, 'F', '计算机网络', 'INTERNET上向朋友发送电子邮件,必须知道对方的真实姓名和家庭住址。', 2);
INSERT INTO `judge_question` VALUES (14, '', 'T', '计算机网络', 'TCP的协议数据单元被称为报文段。', 2);
INSERT INTO `judge_question` VALUES (24, NULL, 'T', '党史', '中国共产党的成立是中国历史上开天辟地的大事变。自从有了中国共产党，中国革命面目就焕然一新了。', 2);
INSERT INTO `judge_question` VALUES (25, NULL, 'F', '党史', '国民革命失败后，民族资产阶级附和了大资产阶级的反革命，成了中国民主革命的对象。', 2);
INSERT INTO `judge_question` VALUES (26, NULL, 'T', '党史', '西安事变的和平解决是中国共产党积极努力的结果，它标志着十年内战局面基本结束。', 2);
INSERT INTO `judge_question` VALUES (27, NULL, 'T', '党史', '抗日战争是中国人民第一次取得完全胜利的民族解放战争。', 2);
INSERT INTO `judge_question` VALUES (28, NULL, 'T', '党史', '中国革命必须分两步走，第一步是新民主主义革命，第二步是社会主义革命，其间不允许横插一个资产阶级专政的阶段。', 2);
INSERT INTO `judge_question` VALUES (29, NULL, 'T', '党史', '刘邓大军强渡黄河，挺进大别山，揭开了解放战争战略决战的序幕。', 2);
INSERT INTO `judge_question` VALUES (30, NULL, 'F', '党史', '中华人民共和国的成立，实现了从新民主主义到社会主义社会的转变。', 2);
INSERT INTO `judge_question` VALUES (31, NULL, 'T', '党史', '和平共处五项原则既适用于不同社会制度国家之间的关系，也适用于社会主义国家之间的关系。', 2);
INSERT INTO `judge_question` VALUES (32, NULL, 'F', '党史', '我国生产资料所有制社会主义改造基本完成以后，国内的主要矛盾发生了转变，阶级斗争就不存在了。', 2);
INSERT INTO `judge_question` VALUES (33, NULL, 'F', '党史', '左”是方法问题，右是立场问题，所以“左”比右好。', 2);
INSERT INTO `judge_question` VALUES (34, NULL, 'T', '党史', '在整个社会主义初级阶段，必须坚持公有制为主体，多种所有制经济共同发展的基本经济制度，而不能搞私有化和单一公有制。', 2);
INSERT INTO `judge_question` VALUES (35, NULL, 'T', '党史', '只有社会主义才能救中国，只有中国特色社会主义才能发展中国，而不能搞民主社会主义和资本主义。', 2);
INSERT INTO `judge_question` VALUES (36, NULL, 'T', '党史', '所谓马克思主义中国化，就是把马克思主义基本原理同中国实际和时代特征结合起来，运用马克思主义的立场、观点、方法研究和解决中国革命、建设、改革中的实际问题。', 2);
INSERT INTO `judge_question` VALUES (37, NULL, 'T', '党史', '社会和谐是中国特色社会主义的本质属性，是国家富强、民族振兴、人民幸福的重要保证。', 2);
INSERT INTO `judge_question` VALUES (38, NULL, 'T', '党史', '党的十五大把“三个代表”重要思想同马克思列宁主义、毛泽东思想、邓小平理论一道确立为我们党必须长期坚持的指导思想，实现了我们党指导思想的又一次与时俱进。', 2);
INSERT INTO `judge_question` VALUES (39, NULL, 'T', '党史', '科学发展观第一次比较系统地回答了什么是社会主义、如何建设社会主义的问题，开拓了马克思主义中国化新境界。', 2);
INSERT INTO `judge_question` VALUES (40, NULL, 'T', '党史', '新时期最鲜明的特点是改革开放，最显著的成就是快速发展，最突出的标志是与时俱进。', 2);
INSERT INTO `judge_question` VALUES (41, NULL, 'T', '党史', '中国特色社会主义理论体系，是马克思主义中国化最新成果，是党最可宝贵的精神财富，是全国各族人民团结奋斗的共同思想基础。', 2);
INSERT INTO `judge_question` VALUES (42, NULL, 'T', '党史', '建设学习型党组织，是建设马克思主义学习型政党的基础工程，是党始终走在时代前列、引领中国发展进步的重要基础。', 2);
INSERT INTO `judge_question` VALUES (43, NULL, 'T', '党史', '党的先进性和党的执政地位都不是一劳永逸、一成不变的，过去先进不等于现在先进，现在先进不等于永远先进;过去拥有不等于现在拥有，现在拥有不等于永远拥有。', 2);

-- ----------------------------
-- Table structure for paper
-- ----------------------------
DROP TABLE IF EXISTS `paper`;
CREATE TABLE `paper`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `paper_id` bigint NULL DEFAULT NULL,
  `question_id` bigint NULL DEFAULT NULL,
  `question_type` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 415 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for score
-- ----------------------------
DROP TABLE IF EXISTS `score`;
CREATE TABLE `score`  (
  `score_id` bigint NOT NULL AUTO_INCREMENT,
  `answer_date` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `course` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `exam_id` bigint NULL DEFAULT NULL,
  `score` int NULL DEFAULT NULL,
  `student_id` bigint NULL DEFAULT NULL,
  PRIMARY KEY (`score_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 21 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for student
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student`  (
  `student_id` bigint NOT NULL,
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `clazz` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `department` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `grade` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `major` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `student_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `pwd` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `role` int NULL DEFAULT NULL,
  PRIMARY KEY (`student_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for teacher
-- ----------------------------
DROP TABLE IF EXISTS `teacher`;
CREATE TABLE `teacher`  (
  `teacher_id` bigint NOT NULL,
  `department` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `teacher_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `pwd` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `role` int NULL DEFAULT NULL,
  PRIMARY KEY (`teacher_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user_face_info
-- ----------------------------
DROP TABLE IF EXISTS `user_face_info`;
CREATE TABLE `user_face_info`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `create_time` datetime(6) NULL DEFAULT NULL,
  `face_feature` blob NULL,
  `face_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `student_id` bigint NULL DEFAULT NULL,
  `update_time` datetime(6) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
