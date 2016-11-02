function [L,U]=lu_decomposition(A)
%   LU·Ö½â
    n=size(A,1);
    L=eye(n);
    U=zeros(n);
    for k=1:n
        U(k,k)=A(k,k);
        for i=k+1:n
            L(i,k)=A(i,k)/U(k,k);
            U(k,i)=A(k,i);
        end
        for i=k+1:n
            for j=k+1:n
                A(i,j)=A(i,j)-L(i,k)*U(k,j);
            end
        end
    end
end