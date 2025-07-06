-- 1. 创建数据库
CREATE DATABASE hotel_db;
GO

USE hotel_db;
GO

-- 2. 创建员工表
CREATE TABLE Employee (
    id INT IDENTITY(1,1) PRIMARY KEY,
    username NVARCHAR(50) NOT NULL UNIQUE,
    password NVARCHAR(100) NOT NULL,
    name NVARCHAR(50),
    role NVARCHAR(20), -- EMPLOYEE, ADMIN
    phone NVARCHAR(20)
);
GO

-- 3. 创建客户表
CREATE TABLE Customer (
    id INT IDENTITY(1,1) PRIMARY KEY,
    username NVARCHAR(50) NOT NULL UNIQUE,
    password NVARCHAR(100) NOT NULL,
    name NVARCHAR(50),
    phone NVARCHAR(20),
    email NVARCHAR(100)
);
GO

-- 4. 创建客房表（支持入住/退房流程）
CREATE TABLE Room (
    id INT IDENTITY(1,1) PRIMARY KEY,
    roomNumber NVARCHAR(20) NOT NULL UNIQUE,
    type NVARCHAR(50),
    price FLOAT,
    status NVARCHAR(20),
    description NVARCHAR(200),
    customer_id INT NULL,
    checkinTime DATETIME NULL,
    CONSTRAINT FK_Room_Customer FOREIGN KEY (customer_id) REFERENCES Customer(id)
);
GO

-- 5. 创建评论表
CREATE TABLE Comment (
    id INT IDENTITY(1,1) PRIMARY KEY,
    content NVARCHAR(1000) NOT NULL,
    createdAt DATETIME,
    customer_id INT,
    room_id INT,
    CONSTRAINT FK_Comment_Customer FOREIGN KEY (customer_id) REFERENCES Customer(id),
    CONSTRAINT FK_Comment_Room FOREIGN KEY (room_id) REFERENCES Room(id)
);
GO

-- 9. 创建入住历史表
CREATE TABLE CheckinRecord (
    id INT IDENTITY(1,1) PRIMARY KEY,
    customer_id INT NOT NULL,
    room_id INT NOT NULL,
    checkinTime DATETIME NOT NULL,
    checkoutTime DATETIME NULL,
    price FLOAT,
    CONSTRAINT FK_CheckinRecord_Customer FOREIGN KEY (customer_id) REFERENCES Customer(id),
    CONSTRAINT FK_CheckinRecord_Room FOREIGN KEY (room_id) REFERENCES Room(id)
);
GO

-- 公告表
CREATE TABLE Announcement (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    content NVARCHAR(1000) NOT NULL,
    createdAt DATETIME DEFAULT GETDATE()
);

-- 6. 插入员工（含管理员和普通员工）
INSERT INTO Employee (username, password, name, role, phone) VALUES
('admin', 'admin123', '管理员', 'ADMIN', '13800000001'),
('staff1', 'staff123', '员工一', 'EMPLOYEE', '13800000002');
GO

-- 7. 插入客户
INSERT INTO Customer (username, password, name, phone, email) VALUES
('customer1', 'cust123', '客户一', '13900000001', 'cust1@example.com'),
('customer2', 'cust456', '客户二', '13900000002', 'cust2@example.com');
GO

-- 8. 插入客房
INSERT INTO Room (roomNumber, type, price, status, description) VALUES
('101', '单人间', 200, '空闲', '靠窗，含早餐'),
('102', '双人间', 300, '已入住', '安静，适合家庭'),
('201', '豪华套房', 600, '空闲', '大床，带浴缸'),
('202', '商务间', 400, '清扫中', '带办公桌，适合商务出行');
GO

-- 9. 插入评论
INSERT INTO Comment (content, createdAt, customer_id, room_id) VALUES
('房间很干净，服务很好！', GETDATE(), 1, 1),
('环境不错，下次还会来。', GETDATE(), 2, 3);
GO 