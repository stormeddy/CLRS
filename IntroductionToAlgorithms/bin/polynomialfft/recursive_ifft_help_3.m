function [y]=recursive_ifft_help_3(a)
%   傅立叶逆变换，a的长度必须为3的幂次
    n=numel(a);
    if n==1
        y=a;
        return;        
    end
    wn=exp(-2*pi*1i/n);
    w=1;
    a1=a(1:3:n-2);
    a2=a(2:3:n-1);
    a3=a(3:3:n);
    y=zeros(n,1);
    y1=recursive_ifft_help_3(a1);
    y2=recursive_ifft_help_3(a2);
    y3=recursive_ifft_help_3(a3);
    w2=exp(-2*pi*1i/3);
    w3=exp(-4*pi*1i/3);
    for k=1:n/3
        y(k)=y1(k)+w*y2(k)+w*w*y3(k);
        y(k+n/3)=y1(k)+w*w2*y2(k)+w*w*w2*w2*y3(k);
        y(k+2*n/3)=y1(k)+w*w3*y2(k)+w*w*w3*w3*y3(k);
        w=w*wn;
    end
end