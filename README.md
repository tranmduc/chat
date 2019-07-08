Chương trình chat đa luồng đơn giản sử dụng Scala.

Chương trình sẽ lưu lại lịch sử chat dưới dạng file .txt

Cách chạy chương trình:
- Chạy file Server.scala
- Chạy nhiều instances của file Client.scala hoặc copy nội dụng file Client.scala sang các file mới "Client1.scala", 'Client2.scala",... và chạy các file này. Ở đây đã tạo sẵn 3 file Client.scala, Client1.scala, Client2.scala để minh họa.

Ở Client, để nhắn tin cho client khác, nhập dưới dạng: message#name
Ví dụ: Client 0 muốn nhắn cho Client 1 tin nhắn với nội dung "hello" thì ở cửa sổ của Client 0, nhập hello#Client 1

Tên các client có dạng mặc định là Client n, ví dụ Client 0, Client 1, Client 2,...

Client 0 có id 0, client 1 có id 1, thì khi Client 0 chat với Client 1, chương trình sẽ tạo file 0-1.txt chứa lịch sử tin nhắn.
