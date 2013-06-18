package files;

public interface OpCodes 
{
	public static final short nop = 0x00;
	public static final short aconst_null = 0x01;
	public static final short iconst_m1 = 0x02;
	public static final short iconst_0 = 0x03;
	public static final short iconst_1 = 0x04;
	public static final short iconst_2 = 0x05;
	public static final short iconst_3 = 0x06;
	public static final short iconst_4 = 0x07;
	public static final short iconst_5 = 0x08;
	public static final short lconst_0 = 0x09;
	public static final short lconst_1 = 0x0a;
	public static final short fconst_0 = 0x0b;
	public static final short fconst_1 = 0x0c;
	public static final short fconst_2 = 0x0d;
	public static final short dconst_0 = 0x0e;
	public static final short dconst_1 = 0x0f;
	public static final short bipush = 0x10;
	public static final short sipush = 0x11;
	public static final short ldc = 0x12;
	public static final short ldc_w = 0x13;
	public static final short ldc2_w = 0x14;
	public static final short iload = 0x15;
	public static final short lload = 0x16;
	public static final short fload = 0x17;
	public static final short dload = 0x18;
	public static final short aload = 0x19;
	public static final short iload_0 = 0x1a;
	public static final short iload_1 = 0x1b;
	public static final short iload_2 = 0x1c;
	public static final short iload_3 = 0x1d;
	public static final short lload_0 = 0x1e;
	public static final short lload_1 = 0x1f;
	public static final short lload_2 = 0x20;
	public static final short lload_3 = 0x21;
	public static final short fload_0 = 0x22;
	public static final short fload_1 = 0x23;
	public static final short fload_2 = 0x24;
	public static final short fload_3 = 0x25;
	public static final short dload_0 = 0x26;
	public static final short dload_1 = 0x27;
	public static final short dload_2 = 0x28;
	public static final short dload_3 = 0x29;
	public static final short aload_0 = 0x2a;
	public static final short aload_1 = 0x2b;
	public static final short aload_2 = 0x2c;
	public static final short aload_3 = 0x2d;
	public static final short iaload = 0x2e;
	public static final short laload = 0x2f;
	public static final short faload = 0x30;
	public static final short daload = 0x31;
	public static final short aaload = 0x32;
	public static final short baload = 0x33;
	public static final short caload = 0x34;
	public static final short saload = 0x35;
	public static final short istore = 0x36;
	public static final short lstore = 0x37;
	public static final short fstore = 0x38;
	public static final short dstore = 0x39;
	public static final short astore = 0x3a;
	public static final short istore_0 = 0x3b;
	public static final short istore_1 = 0x3c;
	public static final short istore_2 = 0x3d;
	public static final short istore_3 = 0x3e;
	public static final short lstore_0 = 0x3f;
	public static final short lstore_1 = 0x40;
	public static final short lstore_2 = 0x41;
	public static final short lstore_3 = 0x42;
	public static final short fstore_0 = 0x43;
	public static final short fstore_1 = 0x44;
	public static final short fstore_2 = 0x45;
	public static final short fstore_3 = 0x46;
	public static final short dstore_0 = 0x47;
	public static final short dstore_1 = 0x48;
	public static final short dstore_2 = 0x49;
	public static final short dstore_3 = 0x4a;
	public static final short astore_0 = 0x4b;
	public static final short astore_1 = 0x4c;
	public static final short astore_2 = 0x4d;
	public static final short astore_3 = 0x4e;
	public static final short iastore = 0x4f;
	public static final short lastore = 0x50;
	public static final short fastore = 0x51;
	public static final short dastore = 0x52;
	public static final short aastore = 0x53;
	public static final short bastore = 0x54;
	public static final short castore = 0x55;
	public static final short sastore = 0x56;
	public static final short pop = 0x57;
	public static final short pop2 = 0x58;
	public static final short dup = 0x59;
	public static final short dup_x1 = 0x5a;
	public static final short dup_x2 = 0x5b;
	public static final short dup2 = 0x5c;
	public static final short dup2_x1 = 0x5d;
	public static final short dup2_x2 = 0x5e;
	public static final short swap = 0x5f;
	public static final short iadd = 0x60;
	public static final short ladd = 0x61;
	public static final short fadd = 0x62;
	public static final short dadd = 0x63;
	public static final short isub = 0x64;
	public static final short lsub = 0x65;
	public static final short fsub = 0x66;
	public static final short dsub = 0x67;
	public static final short imul = 0x68;
	public static final short lmul = 0x69;
	public static final short fmul = 0x6a;
	public static final short dmul = 0x6b;
	public static final short idiv = 0x6c;
	public static final short ldiv = 0x6d;
	public static final short fdiv = 0x6e;
	public static final short ddiv = 0x6f;
	public static final short irem = 0x70;
	public static final short lrem = 0x71;
	public static final short frem = 0x72;
	public static final short drem = 0x73;
	public static final short ineg = 0x74;
	public static final short lneg = 0x75;
	public static final short fneg = 0x76;
	public static final short dneg = 0x77;
	public static final short ishl = 0x78;
	public static final short lshl = 0x79;
	public static final short ishr = 0x7a;
	public static final short lshr = 0x7b;
	public static final short iushr = 0x7c;
	public static final short lushr = 0x7d;
	public static final short iand = 0x7e;
	public static final short land = 0x7f;
	public static final short ior = 0x80;
	public static final short lor = 0x81;
	public static final short ixor = 0x82;
	public static final short lxor = 0x83;
	public static final short iinc = 0x84;
	public static final short i2l = 0x85;
	public static final short i2f = 0x86;
	public static final short i2d = 0x87;
	public static final short l2i = 0x88;
	public static final short l2f = 0x89;
	public static final short l2d = 0x8a;
	public static final short f2i = 0x8b;
	public static final short f2l = 0x8c;
	public static final short f2d = 0x8d;
	public static final short d2i = 0x8e;
	public static final short d2l = 0x8f;
	public static final short d2f = 0x90;
	public static final short i2b = 0x91;
	public static final short i2c = 0x92;
	public static final short i2s = 0x93;
	public static final short lcmp = 0x94;
	public static final short fcmpl = 0x95;
	public static final short fcmpg = 0x96;
	public static final short dcmpl = 0x97;
	public static final short dcmpg = 0x98;
	public static final short ifeq = 0x99;
	public static final short ifne = 0x9a;
	public static final short iflt = 0x9b;
	public static final short ifge = 0x9c;
	public static final short ifgt = 0x9d;
	public static final short ifle = 0x9e;
	public static final short if_icmpeq = 0x9f;
	public static final short if_icmpne = 0xa0;
	public static final short if_icmplt = 0xa1;
	public static final short if_icmpge = 0xa2;
	public static final short if_icmpgt = 0xa3;
	public static final short if_icmple = 0xa4;
	public static final short if_acmpeq = 0xa5;
	public static final short if_acmpne = 0xa6;
	public static final short opgoto = 0xa7;
	public static final short jsr = 0xa8;
	public static final short ret = 0xa9;
	public static final short tableswitch = 0xaa;
	public static final short lookupswitch = 0xab;
	public static final short ireturn = 0xac;
	public static final short lreturn = 0xad;
	public static final short freturn = 0xae;
	public static final short dreturn = 0xaf;
	public static final short areturn = 0xb0;
	public static final short opreturn = 0xb1;
	public static final short getstatic = 0xb2;
	public static final short putstatic = 0xb3;
	public static final short getfield = 0xb4;
	public static final short putfield = 0xb5;
	public static final short invokevirtual = 0xb6;
	public static final short invokespecial = 0xb7;
	public static final short invokestatic = 0xb8;
	public static final short invokeinterface = 0xb9;
	public static final short invokedynamic = 0xba;
	public static final short opnew = 0xbb;
	public static final short newarray = 0xbc;
	public static final short anewarray = 0xbd;
	public static final short arraylength = 0xbe;
	public static final short athrow = 0xbf;
	public static final short checkcast = 0xc0;
	public static final short opinstanceof = 0xc1;
	public static final short monitorenter = 0xc2;
	public static final short monitorexit = 0xc3;
	public static final short wide = 0xc4;
	public static final short multianewarray = 0xc5;
	public static final short ifnull = 0xc6;
	public static final short ifnonnull = 0xc7;
	public static final short goto_w = 0xc8;
	public static final short jsr_w = 0xc9;
	
	public static final String[] names = new String[]
		{
			"nop","aconst_null","iconst_m1","iconst_0","iconst_1","iconst_2","iconst_3","iconst_4","iconst_5",
			"lconst_0","lconst_1","fconst_0","fconst_1","fconst_2","dconst_0","dconst_1","bipush",
			"sipush","ldc","ldc_w","ldc2_w","iload","lload","fload","dload",
			"aload","iload_0","iload_1","iload_2","iload_3","lload_0","lload_1","lload_2",
			"lload_3","fload_0","fload_1","fload_2","fload_3","dload_0","dload_1","dload_2",
			"dload_3","aload_0","aload_1","aload_2","aload_3","iaload","laload","faload",
			"daload","aaload","baload","caload","saload","istore","lstore","fstore",
			"dstore","astore","istore_0","istore_1","istore_2","istore_3","lstore_0","lstore_1",
			"lstore_2","lstore_3","fstore_0","fstore_1","fstore_2","fstore_3","dstore_0","dstore_1",
			"dstore_2","dstore_3","astore_0","astore_1","astore_2","astore_3","iastore","lastore",
			"fastore","dastore","aastore","bastore","castore","sastore","pop","pop2",
			"dup","dup_x1","dup_x2","dup2","dup2_x1","dup2_x2","swap","iadd",
			"ladd","fadd","dadd","isub","lsub","fsub","dsub","imul",
			"lmul","fmul","dmul","idiv","ldiv","fdiv","ddiv","irem",
			"lrem","frem","drem","ineg","lneg","fneg","dneg","ishl",
			"lshl","ishr","lshr","iushr","lushr","iand","land","ior",
			"lor","ixor","lxor","iinc","i2l","i2f","i2d","l2i",
			"l2f","l2d","f2i","f2l","f2d","d2i","d2l","d2f",
			"i2b","i2c","i2s","lcmp","fcmpl","fcmpg","dcmpl","dcmpg",
			"ifeq","ifne","iflt","ifge","ifgt","ifle","if_icmpeq","if_icmpne",
			"if_icmplt","if_icmpge","if_icmpgt","if_icmple","if_acmpeq","if_acmpne","goto","jsr",
			"ret","tableswitch","lookupswitch","ireturn","lreturn","freturn","dreturn","areturn",
			"return","getstatic","putstatic","getfield","putfield","invokevirtual","invokespecial","invokestatic",
			"invokeinterface","invokedynamic","new","newarray","anewarray","arraylength","athrow","checkcast",
			"instanceof","monitorenter","monitorexit","wide","multianewarray","ifnull","ifnonnull","goto_w",
			"jsr_w"
		};

	public static final short[] codes = new short[]
		{
			nop,aconst_null,iconst_m1,iconst_0,iconst_1,iconst_2,iconst_3,iconst_4,iconst_5,
			lconst_0,lconst_1,fconst_0,fconst_1,fconst_2,dconst_0,dconst_1,bipush,
			sipush,ldc,ldc_w,ldc2_w,iload,lload,fload,dload,
			aload,iload_0,iload_1,iload_2,iload_3,lload_0,lload_1,lload_2,
			lload_3,fload_0,fload_1,fload_2,fload_3,dload_0,dload_1,dload_2,
			dload_3,aload_0,aload_1,aload_2,aload_3,iaload,laload,faload,
			daload,aaload,baload,caload,saload,istore,lstore,fstore,
			dstore,astore,istore_0,istore_1,istore_2,istore_3,lstore_0,lstore_1,
			lstore_2,lstore_3,fstore_0,fstore_1,fstore_2,fstore_3,dstore_0,dstore_1,
			dstore_2,dstore_3,astore_0,astore_1,astore_2,astore_3,iastore,lastore,
			fastore,dastore,aastore,bastore,castore,sastore,pop,pop2,
			dup,dup_x1,dup_x2,dup2,dup2_x1,dup2_x2,swap,iadd,
			ladd,fadd,dadd,isub,lsub,fsub,dsub,imul,
			lmul,fmul,dmul,idiv,ldiv,fdiv,ddiv,irem,
			lrem,frem,drem,ineg,lneg,fneg,dneg,ishl,
			lshl,ishr,lshr,iushr,lushr,iand,land,ior,
			lor,ixor,lxor,iinc,i2l,i2f,i2d,l2i,
			l2f,l2d,f2i,f2l,f2d,d2i,d2l,d2f,
			i2b,i2c,i2s,lcmp,fcmpl,fcmpg,dcmpl,dcmpg,
			ifeq,ifne,iflt,ifge,ifgt,ifle,if_icmpeq,if_icmpne,
			if_icmplt,if_icmpge,if_icmpgt,if_icmple,if_acmpeq,if_acmpne,opgoto,jsr,
			ret,tableswitch,lookupswitch,ireturn,lreturn,freturn,dreturn,areturn,
			opreturn,getstatic,putstatic,getfield,putfield,invokevirtual,invokespecial,invokestatic,
			invokeinterface,invokedynamic,opnew,newarray,anewarray,arraylength,athrow,checkcast,
			opinstanceof,monitorenter,monitorexit,wide,multianewarray,ifnull,ifnonnull,goto_w,
			jsr_w
		};

	public int getLengthOf(short op,byte[] next,short ind);
}
