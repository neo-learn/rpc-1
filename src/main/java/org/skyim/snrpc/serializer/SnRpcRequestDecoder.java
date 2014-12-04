package org.skyim.snrpc.serializer;

import java.util.List;

import in.srid.serializer.jackson.JacksonSerializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

/**
 * @author skyim E-mail:wxh64788665@gmail.com
 * @version 创建时间：2014年12月2日 下午2:14:18
 * 类说明
 */
public class SnRpcRequestDecoder extends ByteToMessageDecoder {

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in,
			List<Object> out) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("in.decode()"+in.readableBytes());

		if(in.readableBytes() < 4) {
			return;
		}
		in.markReaderIndex();
		int dataLength = in.readInt();
		if(dataLength<0) {
			ctx.close();
		}
		if(in.readableBytes() < dataLength){
			in.resetReaderIndex();
		}
		byte[] body = new byte[dataLength];
		in.readBytes(body);


	   final JacksonSerializer jackson = new JacksonSerializer();
	   SnRpcRequest snRpcRequest  = jackson.deserialize(body, SnRpcRequest.class);
		   
	   out.add(snRpcRequest);
	}



}
