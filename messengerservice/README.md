简单介绍


============start============

功能介绍

	Messenger是进程间通信的方式，轻量型的IPC方案 底层实现采用AIDL
Messenger 与 AIDL的异同

	
	
1. 都与IPC的调用有关;
2. Messenger 是一种轻量级的 IPC方案，底层实现了AIDL，只是进行了封装，开发的时候不用再写.aidl文件。
3. 都支持实时通信；


1. Messenger一次只能处理一个请求（串行）/AIDL一次可以处理多个请求（并行）；
2. Messenger不支持RPC，只能通过message传递消息/AIDL支持RPC；
3. Messenger使用简单，轻量级，不需要创建AIDL文件/AIDL使用复杂，需要创建AIDL文件；
