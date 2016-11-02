function [y]=recursive_fft(a)
%   傅立叶变换，a的长度必须为2的幂次
    n=numel(a);
    if n==1
        y=a;
        return;        
    end
    wn=exp(2*pi*1i/n);
    w=1;
    a1=a(1:2:n-1);
    a2=a(2:2:n);
    y=zeros(n,1);
    y1=recursive_fft(a1);
    y2=recursive_fft(a2);
    for k=1:n/2
        y(k)=y1(k)+w*y2(k);
        y(k+n/2)=y1(k)-w*y2(k);
        w=w*wn;
    end
end