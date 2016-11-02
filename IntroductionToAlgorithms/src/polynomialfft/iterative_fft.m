function [b]=iterative_fft(a)
%   迭代fft，a的长度必须为2的幂次
    b=bit_reverse_copy(a);
    n=numel(a);
    for s=1:log2(n)
        m=2^s;
        wm=exp(2*pi*1i/m);
        for k=1:m:n
            w=1;
            for j=0:m/2-1
                t=w*b(k+j+m/2);
                u=b(k+j);
                b(k+j)=u+t;
                b(k+j+m/2)=u-t;
                w=w*wm;
            end
        end
    end        
end