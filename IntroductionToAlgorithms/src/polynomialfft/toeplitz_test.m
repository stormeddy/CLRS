% b1=[3 5 9 13];
% b2=[3 11 17 21];
% A=toeplitz(b1,b2)
% n=size(A,1);
% c=zeros(n,1);
% for i=1:n
%     c(i)=(A(1,i)+A(n+1-i,1))/2;
% end
% C=gallery('circul',c)
% B=A-C
% 
% 
% v=[1;2;3;5];
% res=A*v
% v_reverse=flipud(v);
% % v_reverse=[v_reverse;zeros(n,1)];
% % for i=1:n
% %     for j=1:i
% %         A(i,j)=0;
% %     end
% % end
% % resu=polynomial_multiplication([A(1,:) zeros(1,n)],v_reverse)
% resu=conv(A(1,:),v_reverse)
% mu=gallery('circul',resu)
% low=A(n,1:n-1);
% low(1)=0;
% % resl=polynomial_multiplication([low zeros(1,n)],v_reverse)
% resl=conv(low,v_reverse)
% resl=[resl;0];
% mu+repmat(resl,1,2*n-1)

a=[1 2 3 4];
b=[1 7 8 9];
v=[1 2 3 4]';
t=toeplitz(b,a);

n=numel(a);
conv(a,flipud(v))
polynomial_multiplication([a zeros(1,n)],[flipud(v);zeros(n,1)])

low=t;
up=t;
for i=2:n
    for j=1:i-1
        up(i,j)=0;
    end
end

for i=1:n
    for j=i:n
        low(i,j)=0;
    end
end


up
up*v
low
lowv=[7 8 9 0];
conv(lowv,v)
t*v