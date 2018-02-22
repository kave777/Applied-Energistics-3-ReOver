package appeng.core.me.parts.part.cpassthrough;

import appeng.core.me.api.network.block.ConnectUUID;
import appeng.core.me.api.network.block.Connection;
import appeng.core.me.api.network.block.ConnectionPassthrough;
import appeng.core.me.api.parts.PartColor;
import appeng.core.me.network.connect.ConnectionsParams;
import appeng.core.me.parts.part.PartBase;
import net.minecraft.nbt.NBTTagCompound;

public abstract class PartPassthrough<P extends PartPassthrough<P, S>, S extends PartPassthrough.PassthroughState<P, S>> extends PartBase<P, S> {

	protected final PartColor color;
	protected final ConnectionsParams<?> connectionParams;

	public PartPassthrough(PartColor color, ConnectionsParams connectionParams){
		this.color = color;
		this.connectionParams = connectionParams;
	}

	public PartPassthrough(boolean supportsRotation, PartColor color, ConnectionsParams connectionParams){
		super(supportsRotation);
		this.color = color;
		this.connectionParams = connectionParams;
	}

	public static abstract class PassthroughState<P extends PartPassthrough<P, S>, S extends PassthroughState<P, S>> extends PartBase.StateBase<P, S> implements ConnectionPassthrough {

		public PassthroughState(P part){
			super(part);
		}

		/*
		 * Connection
		 */

		protected ConnectUUID pcUUID = new ConnectUUID();

		@Override
		public ConnectUUID getUUIDForConnectionPassthrough(){
			return pcUUID;
		}

		@Override
		public PartColor getColor(){
			return getPart().color;
		}

		@Override
		public boolean test(Connection connection){
			return getPart().connectionParams.getParam(connection) != null;
		}

		@Override
		public <Param extends Comparable<Param>> Param getPassthroughConnectionParameter(Connection<Param, ?> connection){
			return getPart().connectionParams.getParam(connection);
		}

		/*
		 * IO
		 */

		@Override
		public NBTTagCompound serializeNBT(){
			NBTTagCompound nbt = new NBTTagCompound();
			nbt.setTag("pcuuid", pcUUID.serializeNBT());
			return nbt;
		}

		@Override
		public void deserializeNBT(NBTTagCompound nbt){
			pcUUID = ConnectUUID.fromNBT(nbt.getCompoundTag("pcuuid"));
		}

		@Override
		public NBTTagCompound serializeSyncNBT(){
			return new NBTTagCompound();
		}

		@Override
		public void deserializeSyncNBT(NBTTagCompound nbt){

		}

	}

}
