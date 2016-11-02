function [X]=inverse(A)
%   ʹ��lup�ֽ����(α)�����
%   ����α�����ʱ����A'*A�ӽ�������󣬻�����ֵ���ȶ������γ���
    [m,n]=size(A);
    if m==n
        X=zeros(n);
        I=eye(n);
        for i=1:n
            X(:,i)=lup_solve_linear_equation(A,I(:,i));
        end
    else
        X=inverse(A'*A)*A';
    end    
end