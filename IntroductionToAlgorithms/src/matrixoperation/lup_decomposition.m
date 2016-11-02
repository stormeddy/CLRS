function [L,U,pi]=lup_decomposition(A)
    n=size(A);
    pi=1:n;
    L=eye(n);
    U=zeros(n);
    for k=1:n
        p=0;
        for i=k:n
            if abs(A(i,k))>p
                p=abs(A(i,k));
                k_pie=i;
            end
        end
        if p==0
            fprintf('singular matrix');
        end
        temp=pi(k_pie);
        pi(k_pie)=pi(k);
        pi(k)=temp;
        
        line=A(k,:);
        A(k,:)=A(k_pie,:);
        A(k_pie,:)=line;
        
        for i=k+1:n
            A(i,k)=A(i,k)/A(k,k);
            for j=k+1:n
                A(i,j)=A(i,j)-A(i,k)*A(k,j);
            end
        end
    end
    
    for i=2:n
        for j=1:i-1
            L(i,j)=A(i,j);
        end
    end
    
    for i=1:n
        for j=i:n
            U(i,j)=A(i,j);
        end
    end
end
