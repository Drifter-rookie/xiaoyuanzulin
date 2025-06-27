import axios from 'axios';

const service = axios.create({
    timeout: 5000,
    baseURL:  'http://localhost:8088',
    withCredentials:  true
});

service.interceptors.request.use(
    config => {
        // 从localStorage中获取token
        const token = localStorage.getItem('token');
        if (token) {
            // 添加Authorization头部
            config.headers['Authorization'] = `Bearer ${token}`;
            console.log('请求添加token:', config.url);
        } else {
            console.log('请求无token:', config.url);
        }
        return config;
    },
    error => {
        console.error('请求拦截器错误:', error);
        return Promise.reject(error);
    }
);

service.interceptors.response.use(
    response => {
        if (response.status === 200) {
            // 如果响应中包含token，保存到localStorage
            if (response.headers && response.headers.authorization) {
                const token = response.headers.authorization.split(' ')[1];
                localStorage.setItem('token', token);
                console.log('保存响应中的token');
            }
            return response.data;
        } else {
            console.warn('非200响应:', response.status);
            return Promise.reject(response);
        }
    },
    error => {
        console.error('响应错误:', error);
        
        // 处理401未授权错误
        if (error.response && error.response.status === 401) {
            console.warn('401未授权错误');
            // 清除token
            localStorage.removeItem('token');
            
            // 避免在登录页再次跳转到登录页导致循环
            const currentPath = window.location.hash.slice(1); // 获取当前路径
            if (currentPath !== '/login' && !currentPath.startsWith('/login?')) {
                console.log('重定向到登录页');
                // 使用router跳转而不是直接修改location
                setTimeout(() => {
                    window.location.href = '/#/login';
                }, 100);
            } else {
                console.log('已在登录页，不再重定向');
            }
        }
        
        return Promise.reject(error);
    }
);

export default service;
