function [x]=lup_solve(L,U,pi,b)
%   ��֪P,L,U,b,��x
%   P��pi�����ʾ
    n=size(L,1);
    x=zeros(1,n);
    y=zeros(1,n);
    for i=1:n
        y(i)=b(pi(i))-sum(L(i,1:i-1).*y(1:i-1));
    end
    for i=fliplr(1:n)
        x(i)=(y(i)-sum(U(i,i+1:n).*x(i+1:n)))/U(i,i);
    end
    x=x';
end
