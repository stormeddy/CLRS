function ifv=ift_mod_base(base,w,v)
%   ģbase�µĸ���Ҷ��任
%   wΪ����Ԫ
%   vΪҪ�任������
    n=numel(v);
    w=modular_exp(w,n-1,base);  % ģbase�µ�1/w    
    M=ft_mod_matrix(w,n,base);
    n=modular_exp(n,(n-1),base);% ģbase�µ�1/n
    ifv=mod(n*M*v,base);
end